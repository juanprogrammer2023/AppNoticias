const express = require('express');
const router = express.Router();
const db = require('../config/db'); // Ajusta el path si `db.js` está en otro directorio

// Ruta: /api/posts/get-all
router.get('/get-all', (req, res) => {
    // Función: Devuelve todos los posts en la base de datos
    const query = 'SELECT * FROM posts';
    db.query(query, (err, results) => {
        if (err) {
            console.error('Error al obtener los posts:', err);
            return res.status(500).json({ error: 'Error al obtener los posts' });
        }
        res.json(results);
    });
});

// Ruta: /api/posts/get-by-category
router.get('/get-by-category', (req, res) => {
    // Función: Devuelve los posts que pertenecen a una categoría específica con el nombre del usuario
    const categoriaId = req.query.categoria_id;

    if (!categoriaId) {
        return res.status(400).json({ error: 'El parámetro categoria_id es requerido' });
    }

    const query = `
        SELECT 
            posts.id, 
            posts.titulo, 
            posts.contenido, 
            posts.fecha_publicacion, 
            posts.user_id, 
            posts.categoria_id, 
            users.name AS usuario_nombre
        FROM 
            posts
        INNER JOIN 
            users 
        ON 
            posts.user_id = users.id
        WHERE 
            posts.categoria_id = ?;
    `;

    db.query(query, [categoriaId], (err, results) => {
        if (err) {
            console.error('Error al obtener los posts:', err);
            return res.status(500).json({ error: 'Error al obtener los posts' });
        }
        console.log("Consulta recibida");
        console.log(results);
        res.json(results);
    });
});


// Ruta: /api/posts/get-by-id
router.get('/get-by-id/:id', (req, res) => {
    // Función: Devuelve un post específico según su ID
    const postId = req.params.id;

    const query = 'SELECT * FROM posts WHERE id = ?';
    db.query(query, [postId], (err, results) => {
        if (err) {
            console.error('Error al obtener el post:', err);
            return res.status(500).json({ error: 'Error al obtener el post' });
        }

        if (results.length === 0) {
            return res.status(404).json({ error: 'Post no encontrado' });
        }

        res.json(results[0]);
    });
});

// Ruta: /api/posts/create
router.post('/create', (req, res) => {
    // Función: Crea un nuevo post
    const { titulo, contenido, user_id, categoria_id } = req.body;

    if (!titulo || !contenido || !user_id || !categoria_id) {
        return res.status(400).json({ error: 'Todos los campos son requeridos' });
    }

    const query = 'INSERT INTO posts (titulo, contenido, user_id, categoria_id) VALUES (?, ?, ?, ?)';
    db.query(query, [titulo, contenido, user_id, categoria_id], (err, results) => {
        if (err) {
            console.error('Error al crear el post:', err);
            return res.status(500).json({ error: 'Error al crear el post' });
        }

        res.status(201).json({ message: 'Post creado exitosamente', postId: results.insertId });
    });
});

// Ruta: /api/posts/update
router.put('/update/:id', (req, res) => {
    // Función: Actualiza un post existente
    const postId = req.params.id;
    const { titulo, contenido, categoria_id } = req.body;

    if (!titulo || !contenido || !categoria_id) {
        return res.status(400).json({ error: 'Todos los campos son requeridos' });
    }

    const query = 'UPDATE posts SET titulo = ?, contenido = ?, categoria_id = ? WHERE id = ?';
    db.query(query, [titulo, contenido, categoria_id, postId], (err, results) => {
        if (err) {
            console.error('Error al actualizar el post:', err);
            return res.status(500).json({ error: 'Error al actualizar el post' });
        }

        if (results.affectedRows === 0) {
            return res.status(404).json({ error: 'Post no encontrado' });
        }

        res.json({ message: 'Post actualizado exitosamente' });
    });
});

// Ruta: /api/posts/delete
router.delete('/delete/:id', (req, res) => {
    // Función: Elimina un post por ID
    const postId = req.params.id;

    const query = 'DELETE FROM posts WHERE id = ?';
    db.query(query, [postId], (err, results) => {
        if (err) {
            console.error('Error al eliminar el post:', err);
            return res.status(500).json({ error: 'Error al eliminar el post' });
        }

        if (results.affectedRows === 0) {
            return res.status(404).json({ error: 'Post no encontrado' });
        }

        res.json({ message: 'Post eliminado exitosamente' });
    });
});

module.exports = router;
