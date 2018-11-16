import { Injectable } from '@angular/core';
import {Client} from '../model/client';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = 'http://localhost:8181/api/v1/client';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private http: HttpClient) {}

  getAllClients(): Observable<Client[]> {
      return this.http.get<Client[]>(`${BASE_URL}/list`);
  }

  getClientById(id: number): Observable<Client> {
    return this.http.get<Client>(`${BASE_URL}/${id}`);
  }

  getClientByEmail(email: String): Observable<Client> {
    return this.http.get<Client>(`${BASE_URL}/email/${email}`);
  }

  addClient(client: Client): Observable<Client> {
    return this.http.post<Client>(`${BASE_URL}/save`, client);
  }

  updateClient(id: number, client: Client): Observable<Client> {
    return this.http.post<Client>(`${BASE_URL}/update/${id}`, client);
  }

  removeClient(id: number): Observable<string> {
    return this.http.delete(`${BASE_URL}/remove/${id}`, {responseType: 'text'});
  }
}
