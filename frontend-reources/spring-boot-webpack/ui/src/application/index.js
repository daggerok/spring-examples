import angular from 'angular';
import uiRouter from '@uirouter/angularjs';

import ModelsModule from './models';
import ServicesModule from './services';
import ComponentsModule from './components';
import Config from './infrastructure/application.config';

const application = angular.module('app', [
  uiRouter,
  ModelsModule.name,
  ServicesModule.name,
  ComponentsModule.name,
]);

if (process && process.env && process.env.DEVELOPMENT) {
  require('./infrastructure/base.href.config');
}

application.config(['$urlRouterProvider', '$locationProvider', Config]);

export default application;
