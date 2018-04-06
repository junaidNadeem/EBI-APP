export class AppConfig {
  public readonly apiUrl; //backend server URL

  constructor() {
    this.apiUrl = 'http://localhost:8080/api';
  }

  getApiURI() {
    return this.apiUrl;
  }
}
;
