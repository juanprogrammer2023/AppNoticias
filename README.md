<h1>📱 <strong>Aplicación de Posts - AppNoticias</strong></h1>

<p>Bienvenido a <strong>AppNoticias</strong>, una aplicación móvil que permite a los usuarios crear y comentar publicaciones en cuatro categorías: <strong>Deportes</strong>, <strong>Noticias</strong>, <strong>Tecnología</strong> y <strong>Alimentación</strong>.</p>

<p>Esta aplicación está construida con un backend en <strong>Node.js</strong> y un frontend en <strong>Android Studio</strong>, usando tecnologías modernas para una experiencia fluida y rápida.</p>

<hr>

<h2>🚀 <strong>Tecnologías Utilizadas</strong></h2>

<h3><strong>Backend:</strong></h3>
<ul>
    <li><strong>Node.js</strong> con <strong>Express</strong> para la API.</li>
    <li><strong>MySQL</strong> para la base de datos.</li>
    <li><strong>CORS</strong> y <strong>body-parser</strong> para manejar solicitudes HTTP.</li>
    <li><strong>Dotenv</strong> para gestionar variables de entorno.</li>
</ul>

<h3><strong>Frontend:</strong></h3>
<ul>
    <li><strong>Android Studio</strong> (Java/Kotlin) para la creación de la app móvil.</li>
    <li><strong>Retrofit</strong> para la comunicación con la API.</li>
    <li><strong>ViewPager2</strong> y <strong>TabLayout</strong> para la navegación fluida entre las categorías.</li>
</ul>

<hr>

<h2>📂 <strong>Estructura del Proyecto</strong></h2>

<h3><strong>Backend (Node.js + Express)</strong></h3>
<pre>
backend/
│── routes/
│   ├── posts.js        # Rutas para manejar los posts
│   ├── comments.js     # Rutas para manejar los comentarios
│   ├── users.js        # Rutas de autenticación
│── middlewares/
│   ├── authMiddleware.js # Middleware de autenticación
│── database/
│   ├── connection.js   # Conexión con la base de datos MySQL
│── .env                # Variables de entorno (como las credenciales de la base de datos)
│── server.js           # Configuración principal del servidor
</pre>

<h3><strong>Frontend (Android Studio - Java/Kotlin)</strong></h3>
<pre>
frontend/
│── app/
│   ├── activities/
│   │   ├── MainActivity.java       # Pantalla principal con TabLayout
│   │   ├── AddPostActivity.java    # Pantalla para crear nuevos posts
│   ├── fragments/
│   │   ├── PostListFragment.java   # Fragmento para mostrar los posts por categoría
│   ├── adapters/
│   │   ├── ViewPagerAdapter.java   # Adaptador para el ViewPager2
│   ├── network/
│   │   ├── RetrofitClient.java     # Configuración de Retrofit para consumir la API
│   ├── models/
│   │   ├── Post.java               # Modelo de datos para los posts
│   │   ├── Comment.java            # Modelo de datos para los comentarios
│   ├── res/
│   │   ├── layout/                 # Archivos XML para las vistas
│── AndroidManifest.xml              # Configuración de la aplicación (permisos, actividades)
</pre>

<hr>

<h2>🔧 <strong>Instalación y Configuración</strong></h2>

<h3>🖥️ Backend</h3>
<ol>
    <li>Clona el repositorio:
        <pre>git clone https://github.com/juanprogrammer2023/AppNoticias.git</pre>
        <pre>cd project-root</pre>
    </li>
    <li>Instala las dependencias:
        <pre>npm install</pre>
    </li>
    <li>Configura el archivo .env con tus credenciales:
        <pre>PORT=3000</pre>
        <pre>DB_HOST=localhost</pre>
        <pre>DB_USER=root</pre>
        <pre>DB_PASSWORD=tu_password</pre>
        <pre>DB_NAME=Database</pre>
    </li>
    <li>Inicia el servidor:
        <pre>node server.js</pre>
    </li>
</ol>

<h3>📱 Frontend</h3>
<ol>
    <li>Abre el proyecto en <strong>Android Studio</strong>.</li>
    <li>Cambia la IP de tu servidor en <strong>RetrofitClient.java</strong> para que apunte a tu servidor local:
        <pre>private static final String BASE_URL = "http://TU_IP_LOCAL:3000/";</pre>
    </li>
    <li>Compila y ejecuta la aplicación en un emulador o dispositivo físico.</li>
</ol>

<hr>

<h2>📌 <strong>Endpoints Principales</strong></h2>

<h3><strong>Posts</strong></h3>
<ul>
    <li><strong>GET /api/posts</strong> → Obtener todos los posts.</li>
    <li><strong>POST /api/posts</strong> → Crear un nuevo post.</li>
    <li><strong>GET /api/posts/:id</strong> → Obtener un post específico.</li>
    <li><strong>DELETE /api/posts/:id</strong> → Eliminar un post.</li>
</ul>

<h3><strong>Comentarios</strong></h3>
<ul>
    <li><strong>GET /api/comments/:postId</strong> → Obtener los comentarios de un post.</li>
    <li><strong>POST /api/comments</strong> → Agregar un comentario a un post.</li>
</ul>

<h3><strong>Autenticación</strong></h3>
<ul>
    <li><strong>POST /api/users/register</strong> → Registrar un nuevo usuario.</li>
    <li><strong>POST /api/users/login</strong> → Iniciar sesión.</li>
</ul>

<hr>



