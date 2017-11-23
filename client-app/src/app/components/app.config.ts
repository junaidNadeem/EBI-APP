export class AppConfig {
  public readonly apiUrl;

  constructor() {
    this.apiUrl = 'http://localhost:8080/api';
  }

  getApiURI() {
    return this.apiUrl;
  }
}
;
