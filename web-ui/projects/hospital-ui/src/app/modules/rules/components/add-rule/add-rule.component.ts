import { Component, OnInit, ViewChild } from '@angular/core';
import { Rule } from '../../Rule';
import { RuleService } from '../../rule.service';

import * as ace from 'brace';
import 'brace/mode/drools';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-rule',
  templateUrl: './add-rule.component.html',
  styleUrls: ['./add-rule.component.scss']
})
export class AddRuleComponent implements OnInit {

  @ViewChild('editor') editor;

  ruleData: Rule = {
    name:"",
    content: ""
  };

  options = {
    fontSize: "12pt"
  };

  submitted: boolean;

  constructor(
    private ruleService: RuleService,
    private formBuilder: FormBuilder
  ){
   }

  ngOnInit(): void {
    this.submitted = false;
    const eeditor = ace.edit('editor-id');
    eeditor.getSession().setMode('ace/mode/drools');
  }

  getTemplate(event:any){
    this.ruleService.getTemplate().subscribe(data => this.ruleData.content = data);
  }

  onSubmit(){
    console.log(this.ruleData);

    this.ruleService.createRule(this.ruleData)
      .subscribe(
        () => {
          this.submitted = true;
        });
  }

}
