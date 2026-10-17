import {Component, inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AsyncPipe} from '@angular/common';
import {Account, AccountListState, RequestStatus} from '../model/account.model';
import {catchError, map, Observable, of} from 'rxjs';
import {Loading} from '../services/loading';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-accounts',
  imports: [
    AsyncPipe,
    FormsModule
  ],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})



export class Accounts {
  private http = inject(HttpClient);
  public loadingService = inject(Loading);

  accounts$: Observable<AccountListState> = this.http
    .get<Account[]>('http://localhost:9999/EBANK-SERVICE/accounts')
    .pipe(
      map(resp => {
        return {
          accounts: resp,
          statut: RequestStatus.SUCCESS
        };
      }),
      catchError(err => {
        return of({
          accounts: [],
          statut: RequestStatus.ERROR,
          errorMessage: err.statusText
        });
      })
    );
  protected readonly RequestStatus = RequestStatus;


  searchAccountId: string = '';
  searchCustomerId: string = '';
  selectedType: string = 'ALL';

  appliedAccountId: string = '';
  appliedCustomerId: string = '';
  appliedType: string = 'ALL';

  filterAccounts(accounts: Account[] | undefined): Account[] {
    if (!accounts) {
      return [];
    }

    return accounts.filter(acc => {
      const matchesAccountId =
        this.appliedAccountId === '' ||
        String(acc.id).includes(this.appliedAccountId);

      const matchesCustomerId =
        this.appliedCustomerId === '' ||
        String(acc.customerId).includes(this.appliedCustomerId);

      const matchesType =
        this.appliedType === 'ALL' ||
        acc.type === this.appliedType;

      return matchesAccountId &&
        matchesCustomerId &&
        matchesType;
    });
  }

  searchAccounts(): void {
    this.appliedAccountId = this.searchAccountId.trim();
    this.appliedCustomerId = this.searchCustomerId.trim();
    this.appliedType = this.selectedType;

  }

  resetSearch(): void {
    this.searchAccountId = '';
    this.searchCustomerId = '';
    this.selectedType = 'ALL';

    this.appliedAccountId = '';
    this.appliedCustomerId = '';
    this.appliedType = 'ALL';
  }
}

