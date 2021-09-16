import {Component, OnInit, OnDestroy} from '@angular/core';
import {SourceFile} from "../sourceFile";
import {HttpService} from "../service/http.service";
import {Router} from "@angular/router";
import {SourceCode} from "../sourceCode";
import {CompilationResult} from "../compilationResult";
import {interval, Observable, Subscription} from "rxjs";

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit, OnDestroy {
  public editorOptions: {theme: string, language: string};
  public sourceFile : SourceFile;
  public compilationResult: CompilationResult;
  private themeUpdateInterval: Observable<number>;
  private themUpdateSubscription: Subscription;

  constructor(private httpService: HttpService, private router: Router) {
    // passed by SourceFileListComponent via router.navigateByUrl,
    // undefined if page is accessed directly / reloaded
    let passedState = this.router.getCurrentNavigation().extras.state;
    if (!passedState) {
      router.navigateByUrl('ui/projects');
    }
    else {
      this.sourceFile = passedState.sourceFile;

      this.compilationResult = new CompilationResult();
      this.compilationResult.stderr = '';
      this.compilationResult.stdout = '';
      this.compilationResult.compilable = false;

      // default values in case theme or language cannot be set later on
      this.editorOptions = {theme: 'vs-dark', language: 'c'};
      this.updateLanguage();
      this.updateTheme();
      this.periodicallyUpdateTheme(1000);
    }
  }

  ngOnInit(): void {}

  ngOnDestroy(): void {
    if (this.themUpdateSubscription) {
      this.themUpdateSubscription.unsubscribe();
    }
  }

  public saveSourceFile(): void {
    this.httpService.updateSourceFileCode(this.sourceFile.id, this.sourceFile.sourceCode).subscribe(
      response => {},
      error => {
        console.log(error);
      }
    );
  }

  public saveAndCompileSourceFile(): void {
    this.saveSourceFile();
    this.compileSourceFile();
  }

  private updateLanguage(): void {
    let fileExtension = this.sourceFile.name.split('.').pop().toLocaleLowerCase();
    let editorLanguage = 'c';
    if (fileExtension === 'c') {
      editorLanguage = 'c';
    }
    else if (fileExtension === 'java') {
      editorLanguage = 'java';
    }
    else if (fileExtension === 'js') {
      editorLanguage = 'javascript';
    }

    // creating new editorOptions is necessary to make the editor update the theme
    this.editorOptions = {theme: this.editorOptions.theme, language: editorLanguage};
  }

  private updateTheme() {
    let theme: string;

    this.httpService.getIsCurrentlyDark().subscribe(
      (response: boolean) => {
        if (response) {
          theme = 'vs-dark';
        }
        else {
          theme = 'vs-light';
        }

        // only update when theme has actually changed, prevents the editor from flickering on every update
        if (theme !== this.editorOptions.theme) {
          // creating new editorOptions is necessary to make the editor update the theme
          this.editorOptions = {theme: theme, language: this.editorOptions.language};
        }
      },
      error => {
        console.log(error);
      }
    );
  }

  private periodicallyUpdateTheme(periodInMs: number): void {
    this.themeUpdateInterval = interval(periodInMs);
    this.themUpdateSubscription = this.themeUpdateInterval.subscribe(
      response => this.updateTheme()
    )
  }

  private compileSourceFile():void {
    let sourceCode = new SourceCode();
    sourceCode.fileName = this.sourceFile.name;
    sourceCode.code = this.sourceFile.sourceCode;

    this.httpService.compile(sourceCode).subscribe(
      (result: CompilationResult) => {
        this.compilationResult = result;
        // compiler service returns no output if compilation is successful, make success obvious
        if (this.compilationResult.compilable === true &&
            this.compilationResult.stdout.length === 0 && this.compilationResult.stderr.length === 0) {
          this.compilationResult.stdout += 'Compilation successful'
        }
      },
      error => {
        this.compilationResult.compilable = false;
        this.compilationResult.stderr = 'Error: Could not get compilation result from compiler service.\n';
      }
    );
  }
}
