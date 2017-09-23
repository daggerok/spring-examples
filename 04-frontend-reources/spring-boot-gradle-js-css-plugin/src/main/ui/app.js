(function main() {

  /**
   * app
   */

  const name = 'wro4j-app';
  const app = angular.module(name, []);

  /**
   * config
   */

  app.config([
    '$locationProvider',
    $locationProvider => $locationProvider.html5Mode(true)
  ]);

  /**
   * components
   */

  const wro4j1stComponent = {
    template: '' +
      '<p class="heading">first: {{ $ctrl.input }}</p>' +
    '',
    bindings: {
      input: '<'
    },
  };

  app.component('wro4j1stComponent', wro4j1stComponent);

  const wro4j2ndComponent = {
    template: '' +
      '<p class="heading">second</p>' +
    '',
  };

  app.component('wro4j2ndComponent', wro4j2ndComponent);

  const wro4jComponent = {
    template: '' +
      '<h3 class="heading">' +
      '  Hello, wro4j!' +
      '</h3>' +
      '<wro4j1st-component input="\'ololo\'"></wro4j1st-component>' +
      '<wro4j2nd-component></wro4j2nd-component>' +
    '',
  };

  app.component('wro4jComponent', wro4jComponent);

  /**
   * bootstrap
   */

  return angular.bootstrap(document, [app.name], {
    strictDi: true,
    cloak: true,
  });

})();
