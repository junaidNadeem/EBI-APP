import {Injectable} from '@angular/core';
import {Http, Response, Headers, URLSearchParams, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs';
import {Project} from '../models/index';
import {AppConfig} from "../components/app.config";

@Injectable()
export class ProjectService {
  allProjectsUrl = "/all-projects";
  allStudyTypesUrl = "/all-study-types";
  projectUrl = "/project";

  constructor(private http: Http, private appConfig: AppConfig) {
  }

  //Fetch all project-list
  getAllProjects(studyType: string, taxonomyId: string, currentPage: number, itemsPerPage: number): Observable<Project[]> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    let cpParams = new URLSearchParams();
    cpParams.set('currentPage', currentPage.toString());
    cpParams.set('itemsPerPage', itemsPerPage.toString());
    cpParams.set('taxonomyId', taxonomyId.toString());
    cpParams.set('studyType', studyType);
    let options = new RequestOptions({headers: cpHeaders, params: cpParams});
    return this.http.get(this.appConfig.getApiURI() + this.allProjectsUrl, options)
      .map(this.extractData)
      .catch(this.handleError);

  }

  //Fetch all StudyTypes
  getAllStudyTypes(): Observable<string[]> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    return this.http.get(this.appConfig.getApiURI() + this.allStudyTypesUrl)
      .map(this.extractData)
      .catch(this.handleError);

  }

  //Create project-list
  createProject(project: Project): Observable<number> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: cpHeaders});
    return this.http.post(this.appConfig.getApiURI() + this.projectUrl, project, options)
      .map(success => success.status)
      .catch(this.handleError);
  }

  //Fetch project-list by id
  getProjectById(projectId: string): Observable<Project> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    let cpParams = new URLSearchParams();
    cpParams.set('id', projectId);
    let options = new RequestOptions({headers: cpHeaders, params: cpParams});
    return this.http.get(this.appConfig.getApiURI() + this.projectUrl, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  //Update project-list
  updateProject(project: Project): Observable<number> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: cpHeaders});
    return this.http.put(this.appConfig.getApiURI() + this.projectUrl, project, options)
      .map(success => success.status)
      .catch(this.handleError);
  }

  //Delete project-list
  deleteProjectById(projectId: string): Observable<number> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    let cpParams = new URLSearchParams();
    cpParams.set('id', projectId);
    let options = new RequestOptions({headers: cpHeaders, params: cpParams});
    return this.http.delete(this.appConfig.getApiURI() + this.projectUrl, options)
      .map(success => success.status)
      .catch(this.handleError);
  }

  //Data Extractor from json
  private extractData(res: Response) {
    let body = res.json();
    return body;
  }

  private handleError(error: Response | any) {
    console.error(error.message || error);
    return Observable.throw(error.status);
  }
}
