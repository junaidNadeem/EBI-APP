import {SerializationHelper} from "../directives/index";
interface TaxonomySerialized {
  taxonomyId: number;
  taxonomyCommonName: string;
  taxonomyScientificName: string;
}
export class Taxonomy {

  private static _count: number = 0;
  private static _COUNT_KEY: string = 'totalCount';
  private static _TABLE_NAME: string = 'taxonomy';

  constructor(public _taxonomyId: number = -1, public _taxonomyCommonName: string = '', public _taxonomyScientificName: string = '') {
  }

  toJSON(): TaxonomySerialized {
    return {
      taxonomyId: this._taxonomyId,
      taxonomyCommonName: this._taxonomyCommonName,
      taxonomyScientificName: this._taxonomyScientificName
    };
  }

  fromJSON(obj: TaxonomySerialized) {
    this._taxonomyId = obj.taxonomyId
    this._taxonomyCommonName = obj.taxonomyCommonName;
    this._taxonomyScientificName = obj.taxonomyScientificName;
  }

  static fillFromJSON(json: any, isListExpected: boolean) {
    let arrTaxonomy: any;
    if (isListExpected) {
      if (json[Taxonomy._TABLE_NAME] != null) {
        arrTaxonomy = [];
        for (var item in json[Taxonomy._TABLE_NAME]) {
          arrTaxonomy.push(SerializationHelper.toInstance(new Taxonomy(), json[Taxonomy._TABLE_NAME][item]));
        }
      }
    }
    else {
      if (json[Taxonomy._TABLE_NAME] != null)
        arrTaxonomy = SerializationHelper.toInstance(new Taxonomy(), json[Taxonomy._TABLE_NAME]);
    }
    if (json[Taxonomy._COUNT_KEY] != null) {
      Taxonomy.count = json[Taxonomy._COUNT_KEY];
    }
    return arrTaxonomy;
  }

  static get count(): number {
    return this._count;
  }

  static set count(value: number) {
    this._count = value;
  }

  static get COUNT_KEY(): string {
    return this._COUNT_KEY;
  }

  static set COUNT_KEY(value: string) {
    this._COUNT_KEY = value;
  }

  static get TABLE_NAME(): string {
    return this._TABLE_NAME;
  }

  static set TABLE_NAME(value: string) {
    this._TABLE_NAME = value;
  }

  public get taxonomyId(): number {
    return this._taxonomyId;
  }

  public set taxonomyId(value: number) {
    this._taxonomyId = value;
  }

  public get taxonomyCommonName(): string {
    return this._taxonomyCommonName;
  }

  public set taxonomyCommonName(value: string) {
    this._taxonomyCommonName = value;
  }

  public get taxonomyScientificName(): string {
    return this._taxonomyScientificName;
  }

  public set taxonomyScientificName(value: string) {
    this._taxonomyScientificName = value;
  }
}





