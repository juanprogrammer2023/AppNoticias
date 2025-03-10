const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const os = require('os');
const userRoutes = require('./routes/users');
const postRoutes = require('./routes/posts');
const authMiddleware = require('./middlewares/authMiddleware');
const commentsRoutes = require('./routes/comments');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 5000;
const HOST = '0.0.0.0'; // Escucha en todas las interfaces de red

// Función para obtener la dirección IP local
function getLocalIPAddress() {
    const interfaces = os.networkInterfaces();
    for (const iface of Object.values(interfaces)) {
        for (const config of iface) {
            if (config.family === 'IPv4' && !config.internal) {
                return config.address;
            }
        }
    }
    return '127.0.0.1'; // En caso de que no se pueda determinar la IP
}

// Middlewares
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Rutas
app.use('/api/users', userRoutes);
app.use('/api/posts', postRoutes);
app.use('/api/protected', authMiddleware);
app.use('/api/comments', commentsRoutes);

// Ruta para verificar si el servidor está corriendo
app.get('/ping', (req, res) => {
    res.send("Servidor de Juan Cifuentes Corriendo");
});

// Ruta para obtener la dirección IP del servidor
app.get('/ip', (req, res) => {
    res.json({ ip: getLocalIPAddress() });
});

// Iniciar el servidor
app.listen(PORT, HOST, () => {
    const ip = getLocalIPAddress();
    console.log(`Servidor ejecutándose en http://${ip}:${PORT}`);
});
