import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../model/Post';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  // url api
  private url: string = 'http://localhost:8080/posts';

  constructor(private http: HttpClient) { }

  // method to select all posts
  select(): Observable<Post[]> {
    return this.http.get<Post[]>(this.url);
  }
}
