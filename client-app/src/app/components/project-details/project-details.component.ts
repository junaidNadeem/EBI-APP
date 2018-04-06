import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {SlimLoadingBarService} from 'ng2-slim-loading-bar';
import {ProjectService, NotificationService, ActiveTabService, TaxonomyService} from "../../services/index";
import {Project, Taxonomy} from "../../models/index";

@Component({
  moduleId: module.id,
  selector: 'app-project-details',
  templateUrl: 'project-details.component.html'
})
export class ProjectDetailsComponent implements OnInit {
  pageMode: number = 0; //0=view, 1=add, 2=edit
  id: string; //url param

  studyTypes: string[] = [];
  project: Project = new Project;
  taxonomy: Taxonomy = new Taxonomy;
  taxonomyOptions: Taxonomy[] = [];

  constructor(private route: ActivatedRoute, private projectService: ProjectService,
              private router: Router, private taxonomyService: TaxonomyService,
              private notificationService: NotificationService,
              private loadingBarService: SlimLoadingBarService, private activeTabService: ActiveTabService) {
    this.activeTabService.onNavTabClick(2);
  }

  ngOnInit() {
    // (+) converts string 'id' to a number
    this.id = this.route.snapshot.params['id'];
    if (this.router.url.indexOf('edit') >= 0)
      this.pageMode = 2;
    else if (this.router.url.indexOf('view') >= 0)
      this.pageMode = 0;
    else
      this.pageMode = 1;

    if (this.pageMode != 1) //  show details on edit/view mode
      this.loadProjectDetails();
    this.loadTaxonomies();
    this.loadStudyTypes();
  }

  //  fetch study types
  loadStudyTypes() {
    this.loadingBarService.start();
    this.projectService.getAllStudyTypes()
      .subscribe((res) => {
          this.studyTypes = []
          if (res['studyTypes'] != null)
            this.studyTypes = res['studyTypes'];
          this.loadingBarService.complete();
        },
        error => {
          this.loadingBarService.complete();
          this.notificationService.printErrorMessage('Failed to load studyTypes-list. Status: ' + error);
        });
  }

  //  fetch taxonomies
  loadTaxonomies() {
    this.loadingBarService.start();
    this.taxonomyService.getAllTaxonomies(1, 10000)
      .subscribe((res) => {
          this.taxonomyOptions = []
          this.taxonomyOptions = Taxonomy.fillFromJSON(res, true);
          this.loadingBarService.complete();
        },
        error => {
          this.loadingBarService.complete();
          this.notificationService.printErrorMessage('Failed to load taxonomy-list. Status: ' + error);
        });
  }

  //  fetch project details
  loadProjectDetails() {
    this.loadingBarService.start();
    this.projectService.getProjectById(this.id)
      .subscribe((res) => {
          this.project = Project.fillFromJSON(res, false);
          this.taxonomy = Taxonomy.fillFromJSON(res, false);
          this.loadingBarService.complete();
        },
        error => {
          this.loadingBarService.complete();
          this.notificationService.printErrorMessage('Failed to load project-list. ' + error);
        });
  }

  //  update project
  updateProject() {
    this.loadingBarService.start();
    this.project.taxonomyId = this.taxonomy.taxonomyId;
    this.projectService.updateProject(this.project)
      .subscribe(() => {
          this.notificationService.printSuccessMessage('Project has been updated');
          this.loadingBarService.complete();
        },
        error => {
          this.loadingBarService.complete();
          this.notificationService.printErrorMessage('Failed to update project. Status: ' + error);
        });
  }

  //  create project
  createProject() {
    this.loadingBarService.start();
    this.project.taxonomyId = this.taxonomy.taxonomyId;
    this.projectService.createProject(this.project)
      .subscribe(() => {
          this.notificationService.printSuccessMessage('Project has been created');
          this.loadingBarService.complete();
          this.back();
        },
        error => {
          this.loadingBarService.complete();
          this.notificationService.printErrorMessage('Failed to create project. Status: ' + error);
        });
  }

  //  taxonomy dropdown selection => update common name, scientific name
  onTaxonomySelected(tId: number) {
    let obj = this.taxonomyOptions.find(x => x.taxonomyId == tId);
    if (obj)
      this.taxonomy = new Taxonomy(obj.taxonomyId, obj.taxonomyCommonName, obj.taxonomyScientificName);
    else
      this.taxonomy = new Taxonomy;
  }

  back() {
    this.router.navigate(['/project-list']);
  }

}
