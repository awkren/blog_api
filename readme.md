### A simple yet feature-rich blog API.

You'll find the usual blog features you'd expect (create, edit, and delete a post), along with an integrated comments system, making this API very easy to use.

PostgreSQL is used as the database, and it's run using a docker-compose file, making the API portable as there is no need to install anything.

The comments system works by posting a username and content to the ID of a specific post (e.g., /posts/1). When a get request is made to /posts/1, the comments related to that post are shown.

The comments, by default, are stored in a different table and are connected to a desired post by referencing the ID in the posts table.
