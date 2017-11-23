import { Component, ViewContainerRef } from '@angular/core';

import {Subscription} from "rxjs";
import {ActiveTabService} from "../services/active-tab.service";

@Component({
    moduleId: module.id,
    selector: 'ebi-genomics-data',
    templateUrl: 'app.component.html'
})
export class AppComponent {

  activeTabSubscription: Subscription;
  activeTab: number = 1;
    constructor(private viewContainerRef: ViewContainerRef, private activeTabService: ActiveTabService) {
        // You need this small hack in order to catch application root view container ref
      this.activeTabSubscription = this.activeTabService.getNavTabSrc().subscribe(
        activeTab => {
          this.activeTab = activeTab;
        }
      );
        this.viewContainerRef = viewContainerRef;
    }
}
