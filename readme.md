## A simple yet feature-rich blog API.

You'll find the usual blog features you'd expect (create, edit, and delete a post), along with an integrated comments system, making this API very easy to use.

PostgreSQL is used as the database, and it's run using a docker-compose file, making the API portable as there is no need to install anything.

<hr>

### **You can use basic CRUD operations to manage posts**

**POST**

You can create a new post by sending a POST request to `/posts` with the following body:

```json
{
    "title": "The Second Post",
    "content": "This is the second post!"
}
```

**GET**

You can get all posts by sending a GET request to `/posts`:

```
GET /posts
```

Or you can get a specific post by passing an ID:

```
GET /posts/{id}
```

**PUT**

You can edit an existing post by sending a PUT request to `/posts/{id}` and passing the edited fields:

```json
{
    "title": "The First Post",
    "content": "This is indeed the first post!"
}
```

**DELETE**

You can delete an existing post by sending a DELETE request to `/posts/{id}`:

```
DELETE /posts/{id}
```

You can also delete all posts by sending a DELETE to request to `/posts`:

```
DELETE /posts
```

<hr>

### **The comments system works by posting a username and content to the ID of a specific post (e.g., /posts/1).**

You need to make a POST request to:

```
POST /posts/{postId}/comments
```

With the following structure:

```json
{
    "username": "User 2",
    "content": "great post"
}
```

The comments are automatically associated with the post by reference in the database.

**When a get request is made to /posts/1, the comments related to that post are shown.**

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

You can also display only the commentaries of a specific post with:

```
GET /posts/{postId}/comments/{commentaryId}
```

It is also possible to delete commentaries

```
DELETE /posts/{postId}/comments/{commentaryId}
```

<hr>

### **It also has a search functionality that do searches based on keywords. It works by search for matching keywords in either the title or content of a post.**

```
GET /posts/search?keyword=post called
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


We also need to configure a few things in pgAdmin. To do so, go to `localhost:9090` and log in with the credentials in the docker-compose file.

Next, click on `Add New Server`, _Name_ should be `posts` and under _Connection_, the _Host name/address_ should be `pgdatabase`. Also, don't forget to add the password, which in this case would be `123`

The default addresses are:

- **pgAdmin**: 
_localhost:9090_
- **API**:
_localhost:8080/posts_

