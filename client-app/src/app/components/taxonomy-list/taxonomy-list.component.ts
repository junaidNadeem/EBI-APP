import {Component, OnInit, ViewChild, trigger, state, style, animate, transition} from '@angular/core';
import {ActiveTabService, NotificationService, TaxonomyService} from "../../services/index";
import {SlimLoadingBarService} from 'ng2-slim-loading-bar';
import {ModalDirective} from 'ngx-bootstrap';
import {Taxonomy} from "../../models/index";

@Component({
  moduleId: module.id,
  selector: 'app-taxonomy-list',
  templateUrl: 'taxonomy-list.component.html',
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
export class TaxonomyListComponent implements OnInit {
  @ViewChild('childModal') public childModal: ModalDirective;
  taxonomies: Taxonomy[] = [];

  //pagination
  public itemsPerPage: number = 25;
  public totalItems: number = 0;
  public currentPage: number = 1;

  // Modal properties
  @ViewChild('modal')
  modal: any;
  selectedTaxonomyId: number = -1;
  selectedTaxonomy: Taxonomy = new Taxonomy;
  isEditor: boolean = false;

  constructor(private taxonomyService: TaxonomyService, private notificationService: NotificationService,
              private loadingBarService: SlimLoadingBarService, private activeTabService: ActiveTabService) {
    this.activeTabService.onNavTabClick(1);
  }

  ngOnInit() {
    this.loadTaxonomies();
  }

  //fetch taxonomy-list
  loadTaxonomies() {
    this.loadingBarService.start();
    this.taxonomyService.getAllTaxonomies(this.currentPage, this.itemsPerPage)
      .subscribe((res) => {
          this.taxonomies = [];
          this.taxonomies = Taxonomy.fillFromJSON(res, true);
          this.totalItems = Taxonomy.count;
          this.loadingBarService.complete();
        },
        error => {
          this.loadingBarService.complete();
          this.notificationService.printErrorMessage('Failed to load taxonomy-list. Status: ' + error);
        });
  }

  //remove a taxonomy
  removeTaxonomy(taxonomy: Taxonomy) {
    this.notificationService.openConfirmationDialog('Are you sure you want to delete this taxonomy and projects linked to it?',
      () => {
        this.loadingBarService.start();
        this.taxonomyService.deleteTaxonomyById(taxonomy.taxonomyId)
          .subscribe(() => {
              this.notificationService.printSuccessMessage(taxonomy.taxonomyCommonName + ' has been deleted.');
              this.loadingBarService.complete();
              this.loadTaxonomies();
            },
            error => {
              this.loadingBarService.complete();
              this.notificationService.printErrorMessage('Failed to delete ' + taxonomy.taxonomyCommonName + ' Status: ' + error);
            });
      });
  }

  //update a taxonomy
  updateTaxonomy() {
    this.loadingBarService.start();
    this.taxonomyService.updateTaxonomy(this.selectedTaxonomy)
      .subscribe(() => {
          this.notificationService.printSuccessMessage('Taxonomy has been updated');
          this.loadingBarService.complete();
          this.hideChildModal();
          this.loadTaxonomies();
        },
        error => {
          this.loadingBarService.complete();
          this.notificationService.printErrorMessage('Failed to update taxonomy. Status: ' + error);
        });
  }

  //create a taxonomy
  createTaxonomy() {
    this.loadingBarService.start();
    this.taxonomyService.createTaxonomy(this.selectedTaxonomy)
      .subscribe(() => {
          this.notificationService.printSuccessMessage('Taxonomy has been created');
          this.loadingBarService.complete();
          this.hideChildModal();
          this.loadTaxonomies();
        },
        error => {
          this.loadingBarService.complete();
          this.notificationService.printErrorMessage('Failed to create taxonomy. Status: ' + error);
        });
  }

  //modal submit
  submitTaxonomy() {
    if (this.isEditor)
      this.updateTaxonomy();
    else
      this.createTaxonomy();
  }

  //open taxonomy creater modal
  viewTaxonomyCreator() {
    this.selectedTaxonomyId = -1;
    this.isEditor = false;
    this.selectedTaxonomy = new Taxonomy();
    this.childModal.show();
  }

  //open taxonomy editor modal
  viewTaxonomyEditor(id: number) {
    this.selectedTaxonomyId = id;
    this.isEditor = true;
    let taxonomyObj = this.taxonomies.find(obj => obj.taxonomyId == this.selectedTaxonomyId);
    if (taxonomyObj)
      this.selectedTaxonomy = new Taxonomy(taxonomyObj.taxonomyId, taxonomyObj.taxonomyCommonName, taxonomyObj.taxonomyScientificName);
    this.childModal.show();
  }

  onPageSizeChanged(newPageSize) {
    this.currentPage = 1;
    this.itemsPerPage = newPageSize;
    this.loadTaxonomies();
  }

  pageChanged(event: any): void {
    this.currentPage = event.page;
    this.loadTaxonomies();
  };

  public hideChildModal(): void {
    this.childModal.hide();
  }

}
