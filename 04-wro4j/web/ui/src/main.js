import angular from 'angular';

import application from './application';

const angularjsOptions = {
  // instead of data-ng-strict-di=""
  strictDi: true,
  // instead of data-ng-cloak=""
  cloak: true,
};

export default angular.bootstrap(
  document,
  [application.name],
  angularjsOptions,
);
