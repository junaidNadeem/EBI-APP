import {NgModule}      from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpModule}    from '@angular/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {PaginationModule} from 'ngx-bootstrap';
import {DatepickerModule} from 'ngx-bootstrap';
import {ModalModule} from 'ngx-bootstrap';
import {ProgressbarModule} from 'ngx-bootstrap';
import {SlimLoadingBarModule} from 'ng2-slim-loading-bar';
import {TimepickerModule} from 'ngx-bootstrap';

import {AppComponent}   from './app.component';
import {ProjectDetailsComponent} from './project-details/project-details.component';
import {ProjectListComponent} from './project-list/project-list.component';
import {routing} from './app.routes';

import {NotificationService} from '../services/notification.service';
import {ProjectService} from "../services/project.service";
import {TaxonomyListComponent} from './taxonomy-list/taxonomy-list.component';
import {TaxonomyService} from "../services/taxonomy.service";
import {ActiveTabService} from "../services/active-tab.service";
import {AppConfig} from "./app.config";

@NgModule({
  imports: [
    BrowserModule,
    SlimLoadingBarModule.forRoot(),
    BrowserAnimationsModule,
    DatepickerModule.forRoot(),
    FormsModule,
    HttpModule,
    ModalModule.forRoot(),
    ProgressbarModule.forRoot(),
    PaginationModule.forRoot(),
    routing,
    TimepickerModule.forRoot()
  ],
  declarations: [
    AppComponent,
    ProjectDetailsComponent,
    ProjectListComponent,
    TaxonomyListComponent,
  ],
  providers: [
    AppConfig,
    NotificationService,
    ProjectService,
    TaxonomyService,
    ActiveTabService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
