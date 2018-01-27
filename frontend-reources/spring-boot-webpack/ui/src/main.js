import angular from 'angular';

import application from './application';

const angularjsOptions = {
  strictDi: true,
  cloak: true,
};

export default angular.bootstrap(
  document,
  [application.name],
  angularjsOptions,
);
