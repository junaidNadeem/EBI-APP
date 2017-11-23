import {ModuleWithProviders}  from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {ProjectListComponent} from './project-list/project-list.component';
import {ProjectDetailsComponent} from './project-details/project-details.component';
import {TaxonomyListComponent} from "./taxonomy-list/taxonomy-list.component";

const appRoutes: Routes = [
  {path: 'project-list/:id', component: ProjectListComponent},
  {path: 'project-list', component: ProjectListComponent},
  {path: 'project-details/:id/edit', component: ProjectDetailsComponent},
  {path: 'project-details/:id/view', component: ProjectDetailsComponent},
  {path: 'project-details', component: ProjectDetailsComponent},
  {path: 'taxonomy-list', component: TaxonomyListComponent},
  {path: '', redirectTo: 'taxonomy-list', pathMatch: 'full'}
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
