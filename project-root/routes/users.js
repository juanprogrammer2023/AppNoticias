const express = require('express');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const connection = require('../config/db');
require('dotenv').config();

const router = express.Router();

// Registro de usuario
router.post('/register', async (req, res) => {
    const { name, email, password, confirmPassword } = req.body;

    // Verificar que las contraseñas coincidan
    if (password !== confirmPassword) {
        return res.status(400).json({ error: 'Las contraseñas no coinciden' });
    }

    // Verificar formato de correo electrónico
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        return res.status(400).json({ error: 'Correo electrónico inválido' });
    }

    // Verificar fortaleza de la contraseña
    if (password.length < 10) {
        return res.status(400).json({ error: 'La contraseña debe tener al menos 10 caracteres' });
    }

    // Verificar si el correo ya existe
    connection.query('SELECT * FROM users WHERE email = ?', [email], async (err, results) => {
        if (err) return res.status(500).json({ error: 'Error en el servidor' });
        if (results.length > 0) return res.status(400).json({ error: 'El usuario ya existe' });

        // Encriptar contraseña
        const hashedPassword = await bcrypt.hash(password, 10);
        connection.query(
            'INSERT INTO users (name, email, password) VALUES (?, ?, ?)',
            [name, email, hashedPassword],
            (err, results) => {
                if (err) return res.status(500).json({ error: 'Error al registrar el usuario' });
                res.status(201).json({ message: 'Usuario registrado correctamente' });
            }
        );
    });
});


// Inicio de sesión
router.post('/login', async (req, res) => {
    const { email, password } = req.body;

    connection.query('SELECT id, name, email, password FROM users WHERE email = ?', [email], async (err, results) => {
        if (err) return res.status(500).json({ error: 'Error en el servidor' });
        if (results.length === 0) return res.status(404).json({ error: 'Usuario no encontrado' });

        const user = results[0];
        const isMatch = await bcrypt.compare(password, user.password);

        if (!isMatch) return res.status(401).json({ error: 'Contraseña incorrecta' });

        const token = jwt.sign({ id: user.id }, process.env.JWT_SECRET, { expiresIn: '1h' });

        // Respuesta completa con los campos requeridos
        res.json({
            message: 'Inicio de sesión exitoso',
            token,
            userId: user.id,
            username: user.name,
            email: user.email,
        });

        console.log(`Usuario ${user.email} inició sesión correctamente`);
    });
});



module.exports = router;
