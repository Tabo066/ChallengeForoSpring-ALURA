# ChallengeForoSpring-ALURA
Restfull API usando Java, Spring boot, Spring security, Lombok, Mysql, etc e insomnia para testear http requests.<br> 
Intente inplementar Swagger para la documentacion pero tuve muchos problemas de compatibilidad entre dependencias y decidi no hacerlo. <br>
En el login de la api se utiliza el email como "username" y la contraseña debe estar en formato de hash guardada en la base de datos local <br>
para esto existe una clase en la carpeta "test" la cual estuve usando para convertir una contraseña de string a hash "BCryptPasswordHasher".
