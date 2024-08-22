# MiniBankingApp

**MiniBankingApp** bir Java 17 tabanlı mini bankacılık uygulamasıdır. Bu uygulama, temel bankacılık işlevlerini sağlar ve kullanıcıların hesaplarını yönetmelerini, işlem yapmalarını ve kredi bilgilerini takip etmelerini sağlar.

## Özellikler

- **Kullanıcı Yönetimi**: Kullanıcıların hesaplarını oluşturabilir, güncelleyebilir ve görüntüleyebilirsiniz.
- **Kredi Yönetimi**: Kullanıcıların kredi bilgilerini yönetebilir ve takip edebilirsiniz.
- **Tarih ve Zaman Yönetimi**: Kullanıcı ve kredi bilgilerinin oluşturulma ve güncellenme tarihleri otomatik olarak yönetilir.
- **API Endpoints**: RESTful API ile kullanıcı ve kredi bilgilerine erişim sağlar.
- **API Endpoints Paths**: http://localhost:8080/swagger-ui/index.html#.

## Teknolojiler

- **Java 17**: Uygulamanın geliştirme dili. 
- **Spring Boot**: RESTful API'ler oluşturmak için kullanılan çerçeve.
- **JPA/Hibernate**: Veritabanı ile etkileşim için kullanılan ORM aracıdır.
- **Swagger**: API dokümantasyonu ve testleri için kullanılır.
- **PostgreSQL**: Güçlü ve açık kaynaklı bir ilişkisel veritabanı yönetim sistemi olarak kullanılır.
- **Docker**: PostgreSQL veritabanını yönetmek için kullanılır.
- **JUnit**: Birim testleri ve entegrasyon testleri için kullanılır.
- **Mockito**: Testlerde bağımlılıkları taklit etmek için kullanılır.

## Kurulum

### Gereksinimler

- Java 17
- Maven
- Docker
- Docker Compose

## Hazır Requestler
Aşağıda, API'niz için örnek istekler ve yanıtlar verilmiştir.

Kullanıcı Oluşturma
POST /api/users

İstek:

{
"firstName": "John",
"lastName": "Doe"
}

Kredi Oluşturma
POST /api/credits

İstek:
{
"userId": 1,
"amount": 100,
"installmentCount": 5,
"interestRate": 0.02
}







