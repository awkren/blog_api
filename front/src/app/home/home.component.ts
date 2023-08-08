import { Component } from '@angular/core';
import { Post } from '../model/Post';
import { PostService } from '../service/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  // post object
  post = new Post();

  // posts json
  posts: Post[] = [];

  constructor(private service: PostService) { }

  // selection method
  select(): void {
    this.service.select().subscribe(showPosts => this.posts = showPosts);
  }

  // initialization method
  ngOnInit() {
    this.select();
  }
}
