import { Injectable } from '@angular/core';
import {Account} from '../model/account';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';



const BASE_URL = 'http://localhost:8181/api/v1/account/';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) { }


  getAllAccounts(type: String): Observable<Account[]>  {
    return this.http.get<Account[]>(`${BASE_URL}/${type}/list`);
  }

  getAccountById(type: String, id: number): Observable<Account> {
    return this.http.get<Account>(`${BASE_URL}/${type}/${id}`);
  }

  getAllAccountByClientId(type: String, id: number): Observable<Account[]> {
    return this.http.get<Account[]>(`${BASE_URL}/${type}/list/client/${id}`);
  }

  addAccount(type: String, account: Account): Observable<Account> {
    return this.http.post<Account>(`${BASE_URL}/${type}/save`, account);
  }

  updateAccount(type: String, id: number, account: Account): Observable<Account> {
    return this.http.post<Account>(`${BASE_URL}/${type}/update/${id}`, account);
  }

  removeAccount(type: String, id: number): Observable<string> {
    return this.http.delete(`${BASE_URL}/${type}/remove/${id}`, {responseType: 'text'});
  }

  removeAccountByClientId(type: String, id: number): Observable<string> {
    return this.http.delete(`${BASE_URL}/${type}/remove/client/${id}`, {responseType: 'text'});
  }
}
