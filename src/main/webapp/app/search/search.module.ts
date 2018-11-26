import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchComponent } from './search.component';
import { RouterModule } from '@angular/router';
import { SearchRoute } from './search.route';

@NgModule({
    imports: [CommonModule, RouterModule.forChild(SearchRoute)],
    declarations: [SearchComponent]
})
export class SearchModule {}
