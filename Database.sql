-- MySQL dump 10.13  Distrib 9.2.0, for macos14.7 (arm64)
--
-- Host: localhost    Database: AppMoviles
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Deportes','Publicaciones relacionadas con deportes y actividades físicas'),(2,'Noticias','Últimas noticias y eventos actuales'),(3,'Tecnología','Publicaciones sobre avances tecnológicos y gadgets'),(4,'Alimentación','Consejos y noticias sobre alimentación y nutrición');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `user_id` int NOT NULL,
  `comment_text` varchar(500) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (2,1,5,'De verdad que cristiano es el mejor','2024-11-19 18:58:26'),(4,23,4,'Pero nunca se debe dar por muerto al real madrid','2024-11-19 21:36:07'),(5,21,4,'Pecho frio','2024-11-19 21:36:18'),(6,13,4,'Los partidos se ganan mentalmente','2024-11-19 21:36:36'),(7,5,4,'Entrenar con mucha berraquera','2024-11-19 22:16:24'),(8,23,10,'Hola a la clase,esto es un comentario de jaim,e','2024-11-19 23:31:26');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(255) NOT NULL,
  `contenido` text NOT NULL,
  `fecha_publicacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  `categoria_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `categoria_id` (`categoria_id`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `posts_ibfk_2` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'Cr7 es el mejor','Lo es porque no solo es talento,es trabajo','2024-11-19 09:26:19',4,1),(2,'Taxista es golpeado por no dejarse robar','Que feo que ocurran esas cosas','2024-11-19 09:27:50',4,2),(3,'Nodejs es el mejor entorno de ejecucion','No creo que exista uno mas facil','2024-11-19 09:28:53',4,3),(4,'El aguacate posee grasas buenas','A mi me gusta mucho','2024-11-19 09:30:37',4,4),(5,'Cómo mejorar tu rendimiento deportivo','Descubre las mejores técnicas para mejorar tu rendimiento en cualquier deporte.','2024-11-19 10:16:11',4,1),(6,'Noticias de última hora: Tecnología','Apple lanza su nuevo iPhone con características revolucionarias.','2024-11-19 10:16:11',5,3),(7,'Recetas fáciles para una alimentación saludable','Te enseñamos a preparar comidas rápidas y saludables para tu día a día.','2024-11-19 10:16:11',6,4),(8,'Resultados de la liga de fútbol','El equipo local ganó 3-1 en un partido emocionante.','2024-11-19 10:16:11',7,1),(9,'Las últimas tendencias en tecnología móvil','Descubre qué teléfonos inteligentes dominarán el mercado en 2024.','2024-11-19 10:16:11',4,3),(10,'Beneficios del yoga para la mente y el cuerpo','El yoga no solo mejora tu flexibilidad, sino también tu bienestar emocional.','2024-11-19 10:16:11',5,4),(11,'Cómo mantener una dieta equilibrada','Consejos para incluir todos los nutrientes necesarios en tu alimentación diaria.','2024-11-19 10:16:11',6,4),(12,'Lo mejor de la tecnología en 2024','Un vistazo a los gadgets más innovadores que revolucionarán nuestras vidas.','2024-11-19 10:16:11',7,3),(13,'Claves para ganar en deportes de equipo','Cómo la comunicación y la estrategia son esenciales para el éxito.','2024-11-19 10:16:11',4,1),(14,'Alimentos que aumentan tu energía','Conoce qué alimentos debes incluir en tu dieta para sentirte con más energía.','2024-11-19 10:16:11',5,4),(15,'Posible virus puede atacar al mundo','Hay que cuidarse mucho','2024-11-19 14:01:57',5,2),(21,'Messi el mejor','la pulga gano el mundial','2024-11-19 14:28:25',4,1),(22,'El huevo es bueno','Como alimento barato es bueno,ya que no es tan caro como las carnes','2024-11-19 15:39:48',4,4),(23,'Mal rendimiento del real madrid','Los resultados no son los mejores para el equipo de Carlo Ancceloti','2024-11-19 15:44:27',4,1),(24,'Aplicaciones en la nube','Post para el profe','2024-11-19 23:30:55',10,1),(25,'Hola Mundo','Bienvenidos a mi desarrollo de software,programador frontedn y backend,especializado en JavaScript,node js ,vue js','2024-12-10 02:29:11',4,4),(26,'Barcelona es eliminado por el benfica','Sorprendentemente el benfica con un agonico gol de Dimaria al minuto 78\' del partido,logro clasificarse ,eliminando al fc barcelona','2025-03-10 22:00:59',4,1),(27,'Bandeja Paisa','Comi una bandeja paisa de locos','2025-03-10 22:27:15',4,4);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,'Juan Pineda','juanpp282@gmail.com','$2b$10$08ew21XVyikVNtphpb/y4.Sfb/hZky.8j84F2wLhqR.1LSSGmgHfm','2024-11-19 08:42:21','2024-11-19 08:42:21'),(5,'Juan Pérez','juan.perez@example.com','$2b$10$PMn4BHzIwcxwQ0399eBXuOBDc2NvuBSI8rdxBtn7GoUt4YbAW6F6u','2024-11-19 10:12:23','2024-11-19 10:12:23'),(6,'María López','maria.lopez@example.com','$2b$10$L55lch3VXGr9zqgkja7TM.L5VzpUT9PxG8awIadSmIwmYEVOK8j7C','2024-11-19 10:12:32','2024-11-19 10:12:32'),(7,'Carlos Rodríguez','carlos.rodriguez@example.com','$2b$10$3tRjGpYGCT/viQUDJsxnFeqkqpTPzDAQLHM.RfmXT1QlShTmjTeE.','2024-11-19 10:12:47','2024-11-19 10:12:47'),(8,'juanpruebas','juandavidsito@ecci.com','$2b$10$ggisF7z4Il.Zl2OoYw7poeDzhsDxNPuIuiL8kCys9iDGGzCupEGK2','2024-11-19 23:22:09','2024-11-19 23:22:09'),(9,'juancho','juan@gmail.com','$2b$10$3MSTF7c3Qez7yEUvKWRtqOROyKE80tvFZpgLOJ6Y9wKIJ/g7Nj05G','2024-11-19 23:23:52','2024-11-19 23:23:52'),(10,'jaimeavila','jaime.avila@gmail.com','$2b$10$eGlFVILf1qpM.nxRjgMFjubSoTYI.PBfy4gNv/F7p0dHLKy4JQftO','2024-11-19 23:29:47','2024-11-19 23:29:47');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-10 17:57:51
