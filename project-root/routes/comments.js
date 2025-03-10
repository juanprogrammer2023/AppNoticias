const express = require('express');
const router = express.Router();
const connection = require('../config/db'); // Configuración de la base de datos

/**
 * Endpoint para crear un comentario
 * URL: POST /api/comments
 * Body: { "post_id": 1, "user_id": 2, "comment_text": "Este es un comentario" }
 */
router.post('/', (req, res) => {
    const { post_id, user_id, comment_text } = req.body;

    if (!post_id || !user_id || !comment_text) {
        return res.status(400).json({ error: 'Todos los campos son obligatorios' });
    }

    const query = `
        INSERT INTO comments (post_id, user_id, comment_text) 
        VALUES (?, ?, ?)
    `;

    connection.query(query, [post_id, user_id, comment_text], (err, results) => {
        if (err) {
            console.error('Error al crear el comentario:', err);
            return res.status(500).json({ error: 'Error al crear el comentario' });
        }
        res.status(201).json({ message: 'Comentario creado exitosamente', commentId: results.insertId });
    });
});

/**
 * Endpoint para obtener los comentarios de un post
 * URL: GET /api/comments/:post_id
 */
router.get('/:postId', (req, res) => {
    const { postId } = req.params;

    const query = `
        SELECT comments.id, comments.comment_text, comments.created_at, users.name AS username
        FROM comments
        JOIN users ON comments.user_id = users.id
        WHERE comments.post_id = ?
    `;

    connection.query(query, [postId], (err, results) => {
        if (err) {
            console.error('Error al obtener los comentarios:', err);
            return res.status(500).json({ error: 'Error al obtener los comentarios' });
        }
        console.log(results)
        res.json(results);
    });
});


/**
 * Endpoint para eliminar un comentario
 * URL: DELETE /api/comments/:id
 */
router.delete('/:id', (req, res) => {
    const { id } = req.params;

    if (!id) {
        return res.status(400).json({ error: 'El ID del comentario es obligatorio' });
    }

    const query = `DELETE FROM comments WHERE id = ?`;

    connection.query(query, [id], (err, results) => {
        if (err) {
            console.error('Error al eliminar el comentario:', err);
            return res.status(500).json({ error: 'Error al eliminar el comentario' });
        }

        if (results.affectedRows === 0) {
            return res.status(404).json({ error: 'Comentario no encontrado' });
        }

        res.json({ message: 'Comentario eliminado exitosamente' });
    });
});

/**
 * Endpoint para eliminar un post y sus comentarios relacionados
 * URL: DELETE /api/posts/:id
 */
router.delete('/posts/:id', (req, res) => {
    const { id } = req.params;

    if (!id) {
        return res.status(400).json({ error: 'El ID del post es obligatorio' });
    }

    const deleteCommentsQuery = `DELETE FROM comments WHERE post_id = ?`;
    const deletePostQuery = `DELETE FROM posts WHERE id = ?`;

    // Primero eliminar los comentarios relacionados al post
    connection.query(deleteCommentsQuery, [id], (err) => {
        if (err) {
            console.error('Error al eliminar comentarios:', err);
            return res.status(500).json({ error: 'Error al eliminar comentarios relacionados al post' });
        }

        // Luego eliminar el post
        connection.query(deletePostQuery, [id], (err, results) => {
            if (err) {
                console.error('Error al eliminar el post:', err);
                return res.status(500).json({ error: 'Error al eliminar el post' });
            }

            if (results.affectedRows === 0) {
                return res.status(404).json({ error: 'Post no encontrado' });
            }

            res.json({ message: 'Post y sus comentarios eliminados exitosamente' });
        });
    });
});

module.exports = router;
