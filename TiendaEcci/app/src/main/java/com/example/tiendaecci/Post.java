package com.example.tiendaecci;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Post {
    private int id;
    private String titulo;
    private String contenido;
    private Date fechaPublicacion;
    private int userId;
    private int categoriaId;

    @SerializedName("usuario_nombre") // Mapeo del campo de la respuesta JSON
    private String usuarioNombre;

    // Constructor vacío
    public Post() {}

    // Constructor completo
    public Post(int id, String titulo, String contenido, Date fechaPublicacion, int userId, int categoriaId, String usuarioNombre) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
        this.userId = userId;
        this.categoriaId = categoriaId;
        this.usuarioNombre = usuarioNombre;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }
}
