import {Injectable} from '@angular/core';
import {Http, Response, Headers, URLSearchParams, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs';
import {Taxonomy} from '../models/index';
import {AppConfig} from "../components/app.config";

@Injectable()
export class TaxonomyService {
  allTaxonomiesUrl = "/all-taxonomies";
  taxonomyUrl = "/taxonomy";

  constructor(private http: Http, private appConfig: AppConfig) {
  }

  //Fetch all taxonomy-list
  getAllTaxonomies(currentPage: number, itemsPerPage: number): Observable< Taxonomy[]> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    let cpParams = new URLSearchParams();
    cpParams.set('currentPage', currentPage.toString());
    cpParams.set('itemsPerPage', itemsPerPage.toString());
    let options = new RequestOptions({headers: cpHeaders, params: cpParams});
    return this.http.get(this.appConfig.getApiURI() + this.allTaxonomiesUrl, options)
      .map(this.extractData)
      .catch(this.handleError);

  }

  //Create taxonomy-list
  createTaxonomy(taxonomy: Taxonomy): Observable<number> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: cpHeaders});
    return this.http.post(this.appConfig.getApiURI() + this.taxonomyUrl, taxonomy, options)
      .map(success => success.status)
      .catch(this.handleError);
  }

  //Fetch taxonomy-list by id
  getTaxonomyById(taxonomyId: number): Observable< Taxonomy> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    let cpParams = new URLSearchParams();
    cpParams.set('id', taxonomyId.toString());
    let options = new RequestOptions({headers: cpHeaders, params: cpParams});
    return this.http.get(this.appConfig.getApiURI() + this.taxonomyUrl, options)
      .map(this.extractData)
      .catch(this.handleError);
  }

  //Update taxonomy-list
  updateTaxonomy(taxonomy: Taxonomy): Observable<number> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    let options = new RequestOptions({headers: cpHeaders});
    return this.http.put(this.appConfig.getApiURI() + this.taxonomyUrl, taxonomy, options)
      .map(success => success.status)
      .catch(this.handleError);
  }

  //Delete taxonomy-list
  deleteTaxonomyById(taxonomyId: number): Observable<number> {
    let cpHeaders = new Headers({'Content-Type': 'application/json'});
    let cpParams = new URLSearchParams();
    cpParams.set('id', taxonomyId.toString());
    let options = new RequestOptions({headers: cpHeaders, params: cpParams});
    return this.http.delete(this.appConfig.getApiURI() + this.taxonomyUrl, options)
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
