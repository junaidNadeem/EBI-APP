import {SerializationHelper} from "../directives/index";
interface ProjectSerialized {
  projectId: string;
  title: string;
  description: string;
  sourceType: string;
  studyType: string;
  evaCenterName: string;
  centerName: string;
  taxonomyId: number;
}
export class Project {

  private static _count: number = 0;
  private static _COUNT_KEY: string = 'totalCount';
  private static _TABLE_NAME: string = 'project';

  constructor(public _projectId: string = '', public _title: string = '', public _description: string = '',
              public _sourceType: string = '', public _studyType: string = '-1', public _evaCenterName: string = '',
              public _centerName: string = '', public _taxonomyId: number = 0) {
  }

  toJSON(): ProjectSerialized {
    return {
      projectId: this._projectId,
      title: this._title,
      description: this._description,
      sourceType: this._sourceType,
      studyType: this._studyType,
      evaCenterName: this._evaCenterName,
      centerName: this._centerName,
      taxonomyId: this._taxonomyId
    };
  }

  fromJSON(obj: ProjectSerialized) {
    this._projectId = obj.projectId
    this._title = obj.title;
    this._description = obj.description;
    this._sourceType = obj.sourceType;
    this._studyType = obj.studyType;
    this._evaCenterName = obj.evaCenterName;
    this._centerName = obj.centerName;
    this._taxonomyId = obj.taxonomyId;
  }

  static fillFromJSON(json: any, isListExpected: boolean) {
    let arrProject: any;
    if (isListExpected) {
      if (json[Project._TABLE_NAME] != null) {
        arrProject = []
        for (var item in json[Project._TABLE_NAME])
          arrProject.push(SerializationHelper.toInstance(new Project(), json[Project._TABLE_NAME][item]));
      }
    }
    else {
      if (json[Project._TABLE_NAME] != null)
        arrProject = SerializationHelper.toInstance(new Project(), json[Project._TABLE_NAME]);
    }

    if (json[Project._COUNT_KEY] != null)
      Project.count = json[Project._COUNT_KEY];
    return arrProject;
  }

  public get projectId(): string {
    return this._projectId;
  }

  public set projectId(value: string) {
    this._projectId = value;
  }

  public get title(): string {
    return this._title;
  }

  public set title(value: string) {
    this._title = value;
  }

  public get description(): string {
    return this._description;
  }

  public set description(value: string) {
    this._description = value;
  }

  public get sourceType(): string {
    return this._sourceType;
  }

  public set sourceType(value: string) {
    this._sourceType = value;
  }

  public get studyType(): string {
    return this._studyType;
  }

  public set studyType(value: string) {
    this._studyType = value;
  }

  public get evaCenterName(): string {
    return this._evaCenterName;
  }

  public set evaCenterName(value: string) {
    this._evaCenterName = value;
  }

  public get centerName(): string {
    return this._centerName;
  }

  public set centerName(value: string) {
    this._centerName = value;
  }

  public get taxonomyId(): number {
    return this._taxonomyId;
  }

  public set taxonomyId(value: number) {
    this._taxonomyId = value;
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
}





