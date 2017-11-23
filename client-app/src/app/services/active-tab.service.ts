import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Rx';
import {Subject}    from 'rxjs/Subject';

@Injectable()
export class ActiveTabService {

  // Observable string sources
  private navTabSrc = new Subject<number>();

  constructor() {
  }

  getNavTabSrc(): Observable<any> {
    return this.navTabSrc.asObservable();
  }


  // Service message commands
  onNavTabClick(activeTab: number) {
    this.navTabSrc.next(activeTab);
  }

}



