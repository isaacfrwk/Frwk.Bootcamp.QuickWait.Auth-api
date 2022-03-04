Restrição dos atributos: 

(nullable = false, unique = true)
@Size(min = 4, max = 20)
username;

(nullable = false, unique = true)
@Size(min = 15, max = 50)
email;

(nullable = false)
@Size(max = 120)
password;

(unique = true)
@Size(min = 11, max = 11)
cpf;

@Size(min = 8, max = 11)
phoneNumber;

@Size(min = 8, max = 8)
birthDate;
