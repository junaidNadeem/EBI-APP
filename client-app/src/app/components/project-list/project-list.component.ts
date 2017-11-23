import {Component, OnInit, ViewChild, trigger, state, style, animate, transition} from '@angular/core';
import {ActiveTabService, TaxonomyService, NotificationService, ProjectService} from "../../services/index";
import {SlimLoadingBarService} from 'ng2-slim-loading-bar';
import {Taxonomy, Project} from "../../models/index";
import {ActivatedRoute} from "@angular/router";
import {ModalDirective} from 'ngx-bootstrap';

@Component({
  moduleId: module.id,
  selector: 'app-project-list',
  templateUrl: 'project-list.component.html',
  animations: [
    trigger('flyInOut', [
      state('in', style({opacity: 1, transform: 'translateX(0)'})),
      transition('void => *', [
        style({
          opacity: 0,
          transform: 'translateX(-100%)'
        }),
        animate('0.5s ease-in')
      ]),
      transition('* => void', [
        animate('0.2s  ease-out', style({
          opacity: 0,
          transform: 'translateX(100%)'
        }))
      ])
    ])
  ]
})
export class ProjectListComponent implements OnInit {
  @ViewChild('childModal') public childModal: ModalDirective;
  subscription: any; //to get url param

  taxonomies: Taxonomy[] = [];
  projects: Project[] = [];
  studyTypes: string[] = [];
  taxonomyIdSelected: string = "-1";
  studyTypeSelected: string = "-1";

  //pagination
  public itemsPerPage: number = 25;
  public totalItems: number = 0;
  public currentPage: number = 1;

  // Modal properties
  @ViewChild('modal')
  modal: any;
  index: number = 0;

  constructor(private projectService: ProjectService, private taxonomyService: TaxonomyService,
              private notificationService: NotificationService,
              private loadingBarService: SlimLoadingBarService,
              private route: ActivatedRoute, private activeTabService: ActiveTabService) {
    this.activeTabService.onNavTabClick(2);
  }

  ngOnInit() {
    this.subscription = this.route.params.subscribe(params => {
      this.taxonomyIdSelected = params['id'] || '-1'; // (+) converts string 'id' to a number
    });
    this.loadTaxonomies();
    this.loadStudyTypes();
    this.loadProjects(this.studyTypeSelected, this.taxonomyIdSelected, this.currentPage, this.itemsPerPage);
  }

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

  loadTaxonomies() {
    this.loadingBarService.start();
    this.taxonomyService.getAllTaxonomies(1, 10000)
      .subscribe((res) => {
          this.taxonomies = []
          this.taxonomies = Taxonomy.fillFromJSON(res, true);
          this.loadingBarService.complete();
        },
        error => {
          this.loadingBarService.complete();
          this.notificationService.printErrorMessage('Failed to load taxonomy-list. Status: ' + error);
        });
  }

  loadProjects(studyType: string, taxonomyId: string, currentPage: number, itemsPerPage: number) {
    this.loadingBarService.start();
    this.projectService.getAllProjects(studyType, taxonomyId, currentPage, itemsPerPage)
      .subscribe((res) => {
          this.projects = []
          this.projects = Project.fillFromJSON(res, true);// project-list;
          this.totalItems = Project.count;
          this.loadingBarService.complete();
        },
        error => {
          this.loadingBarService.complete();
          this.notificationService.printErrorMessage('Failed to load project-list. Status: ' + error);
        });
  }

  removeProject(project: Project) {
    this.notificationService.openConfirmationDialog('Are you sure you want to delete this taxonomy and projects linked to it?',
      () => {
        this.loadingBarService.start();
        this.projectService.deleteProjectById(project.projectId)
          .subscribe(() => {
              this.notificationService.printSuccessMessage(project.title + ' has been deleted.');
              this.loadingBarService.complete();
              this.loadProjects(this.studyTypeSelected, this.taxonomyIdSelected, this.currentPage, this.itemsPerPage);
            },
            error => {
              this.loadingBarService.complete();
              this.notificationService.printErrorMessage('Failed to delete ' + project.title + ' Status: ' + error);
            });
      });
  }

  onTaxonomySelected(taxonomyId: string) {
    this.loadProjects(this.studyTypeSelected, taxonomyId, this.currentPage, this.itemsPerPage);
  }

  onStudyTypeSelected(studyType: string) {
    this.loadProjects(studyType, this.taxonomyIdSelected, this.currentPage, this.itemsPerPage);
  }

  pageChanged(event: any): void {
    this.currentPage = event.page;
    this.loadProjects(this.studyTypeSelected, this.taxonomyIdSelected, this.currentPage, this.itemsPerPage);
  };

  onPageSizeChanged(newPageSize) {
    this.currentPage = 1;
    this.itemsPerPage = newPageSize;
    this.loadProjects(this.studyTypeSelected, this.taxonomyIdSelected, this.currentPage, this.itemsPerPage);
  }

  public hideChildModal(): void {
    this.childModal.hide();
  }
}
