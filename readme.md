## A simple yet feature-rich blog API.

You'll find the usual blog features you'd expect (create, edit, and delete a post), along with an integrated comments system, making this API very easy to use.

PostgreSQL is used as the database, and it's run using a docker-compose file, making the API portable as there is no need to install anything.

<hr>

**The comments system works by posting a username and content to the ID of a specific post (e.g., /posts/1).**

You need to make a POST request to:

```
/posts/{postId}/comments
```

With the following structure:

```
"username": "User 2",
"content": "great post"
```

The comments are automatically associated with the post by reference in the database.

### **When a get request is made to /posts/1, the comments related to that post are shown.**

```json
{
    "id": 1,
    "title": "The First Post",
    "content": "This is indeed the first post!",
    "comments": [
        {
            "id": 1,
            "username": "User 1",
            "content": "that's cool!"
        },
        {
            "id": 2,
            "username": "User 2",
            "content": "great post"
        }
    ]
}
```

<hr>

### **It also has a search functionality that do searches based on keywords. It works by search for matching keywords in either the title or content of a post.**

```
/posts/search?keyword=post called
```

It should return something like this:

```json
[
    {
        "id": 2,
        "title": "This is the title",
        "content": "This is the content of the post called 'This is the title'",
        "comments": [
            {
                "id": 1,
                "username": "ren",
                "content": "Great! It's working"
            }
        ]
    }
]
```

## How to run it

As we'll be using a docker-compose file with PostgreSQL and pgAdmin in it, there's no need to install it. Just remember to create a volume using the same name in the docker-compose file.

```
docker volume create blogpost
```

Now we initialize the docker-compose file:

```
docker-compose up -d
```

And finally execute the Spring Boot application:

```
mvn spring-boot:run
```

Or if you don't have Maven installed on your machine, run the executable file:

```
./mvnw spring-boot:run
```

The default addresses are:

- **pgAdmin**: 
_localhost:9090_
- **API**:
_localhost:8080/posts_
