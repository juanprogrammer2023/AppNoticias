package com.example.tiendaecci;

public class PostRequest {
    private String titulo;
    private String contenido;
    private int user_id;
    private int categoria_id;

    public PostRequest(String titulo, String contenido, int user_id, int categoria_id) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.user_id = user_id;
        this.categoria_id = categoria_id;
    }

    @Override
    public String toString() {
        return "PostRequest{" +
                "titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", user_id=" + user_id +
                ", categoria_id=" + categoria_id +
                '}';
    }
}

