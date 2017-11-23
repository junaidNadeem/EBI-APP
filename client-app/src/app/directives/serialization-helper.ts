/**
 * Created by shiraz on 14-Jul-17.
 */

export class SerializationHelper {
  static toInstance<T>(obj: T, json: any): T {
    var jsonObj = (json);
    // var jsonObj = JSON.parse(json);
    if (typeof obj["fromJSON"] === "function") {
      obj["fromJSON"](jsonObj);
    }
    else {
      for (var propName in jsonObj) {
        obj[propName] = jsonObj[propName]
      }
    }
    return obj;
  }
}
