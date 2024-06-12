let service: OnAccountantService;
let httpMock: HttpTestingController;

beforeEach(() => {
  TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule, TranslateTestingModule.withTranslations({})],
  });
  service = TestBed.inject(OnAccountantService);
  httpMock = TestBed.inject(HttpTestingController);
  TestBed.inject(OnAlertService);
  TestBed.inject(Router);

  it('should get by cpf', () => {
    service.getByCpf('84736631015').subscribe(response => {
      expect(response).toEqual({});
    });

    const http = httpMock.expectOne('/api/br-client/v1/accountants/search/cpf');
    expect(http.request.method).toBe('POST');
    expect(http.request.body).toEqual({ cpf: '84736631015' });
    http.flush({});
  });
});