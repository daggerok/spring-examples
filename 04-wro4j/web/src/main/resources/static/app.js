w([0],[
/* 0 */,
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */,
/* 9 */,
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("/* WEBPACK VAR INJECTION */(function(process) {\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _angular = __webpack_require__(0);\n\nvar _angular2 = _interopRequireDefault(_angular);\n\nvar _angularjs = __webpack_require__(3);\n\nvar _angularjs2 = _interopRequireDefault(_angularjs);\n\nvar _models = __webpack_require__(27);\n\nvar _models2 = _interopRequireDefault(_models);\n\nvar _services = __webpack_require__(29);\n\nvar _services2 = _interopRequireDefault(_services);\n\nvar _components = __webpack_require__(22);\n\nvar _components2 = _interopRequireDefault(_components);\n\nvar _application = __webpack_require__(23);\n\nvar _application2 = _interopRequireDefault(_application);\n\nfunction _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }\n\nvar application = _angular2.default.module('app', [_angularjs2.default, _models2.default.name, _services2.default.name, _components2.default.name]);\n\n// just for karma testing weh error about tag base is required occurs:\nif (process && __webpack_require__.i({\"NODE_ENV\":\"development\",\"DEVELOPMENT\":true}) && true) {\n  __webpack_require__(24);\n}\n\napplication.config(['$urlRouterProvider', '$locationProvider', _application2.default]);\n\nexports.default = application;\nmodule.exports = exports['default'];\n/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(5)))\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/index.js\n// module id = 10\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/index.js?");

/***/ }),
/* 11 */,
/* 12 */,
/* 13 */,
/* 14 */,
/* 15 */
/***/ (function(module, exports) {

eval("// removed by extract-text-webpack-plugin\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/components/home/home.component.styl\n// module id = 15\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/components/home/home.component.styl?");

/***/ }),
/* 16 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nexports.default = function ($stateProvider) {\n  return $stateProvider.state({\n    // url: '',\n    name: 'app',\n    abstract: true,\n    template: '<app></app>'\n  });\n};\n\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/components/app.component.config.js\n// module id = 16\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/components/app.component.config.js?");

/***/ }),
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n/*eslint quotes: [\"error\", \"backtick\"]*/\n/*eslint-env es6*/\nexports.default = { template: \"<ui-view/>\" };\nmodule.exports = exports[\"default\"];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/components/app.component.js\n// module id = 17\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/components/app.component.js?");

/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nexports.default = function ($stateProvider) {\n  return $stateProvider.state({\n    url: '/',\n    name: 'app.home',\n    template: '<app-home></app-home>'\n  });\n};\n\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/components/home/home.component.config.js\n// module id = 18\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/components/home/home.component.config.js?");

/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if (\"value\" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();\n\nfunction _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError(\"Cannot call a class as a function\"); } }\n\nvar HomeController = function () {\n  function HomeController() {\n    _classCallCheck(this, HomeController);\n\n    this.$ctrl = this;\n  }\n\n  _createClass(HomeController, [{\n    key: '$onInit',\n    value: function $onInit() {\n      this.greeting = this.first = 'hi';\n      this.second = 'yay!';\n    }\n  }, {\n    key: 'toggleGreeting',\n    value: function toggleGreeting() {\n      this.greeting = this.greeting === this.first ? this.second : this.first;\n    }\n  }]);\n\n  return HomeController;\n}();\n\nexports.default = HomeController;\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/components/home/home.component.controller.js\n// module id = 19\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/components/home/home.component.controller.js?");

/***/ }),
/* 20 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\n__webpack_require__(15);\n\nvar _homeComponent = __webpack_require__(32);\n\nvar _homeComponent2 = _interopRequireDefault(_homeComponent);\n\nvar _homeComponent3 = __webpack_require__(19);\n\nvar _homeComponent4 = _interopRequireDefault(_homeComponent3);\n\nfunction _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }\n\nexports.default = {\n  template: _homeComponent2.default,\n  controller: _homeComponent4.default\n};\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/components/home/home.component.js\n// module id = 20\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/components/home/home.component.js?");

/***/ }),
/* 21 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _angular = __webpack_require__(0);\n\nvar _angular2 = _interopRequireDefault(_angular);\n\nvar _homeComponent = __webpack_require__(18);\n\nvar _homeComponent2 = _interopRequireDefault(_homeComponent);\n\nvar _home = __webpack_require__(20);\n\nvar _home2 = _interopRequireDefault(_home);\n\nfunction _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }\n\nexports.default = _angular2.default.module('app-home.component', []).component('appHome', _home2.default).config(['$stateProvider', _homeComponent2.default]);\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/components/home/index.js\n// module id = 21\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/components/home/index.js?");

/***/ }),
/* 22 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _angular = __webpack_require__(0);\n\nvar _angular2 = _interopRequireDefault(_angular);\n\nvar _appComponent = __webpack_require__(16);\n\nvar _appComponent2 = _interopRequireDefault(_appComponent);\n\nvar _app = __webpack_require__(17);\n\nvar _app2 = _interopRequireDefault(_app);\n\nvar _home = __webpack_require__(21);\n\nvar _home2 = _interopRequireDefault(_home);\n\nfunction _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }\n\n// root component:\nvar app = _angular2.default.module('app.component', [_home2.default.name]);\n\napp.component('app', _app2.default);\napp.config(['$stateProvider', _appComponent2.default]);\n\nexports.default = app;\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/components/index.js\n// module id = 22\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/components/index.js?");

/***/ }),
/* 23 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nexports.default = function ($urlRouterProvider, $locationProvider) {\n  $urlRouterProvider.otherwise('/');\n  //$locationProvider.hashPrefix('!');\n  $locationProvider.html5Mode(true);\n};\n\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/infrastructure/application.config.js\n// module id = 23\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/infrastructure/application.config.js?");

/***/ }),
/* 24 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _angular = __webpack_require__(0);\n\nvar _angular2 = _interopRequireDefault(_angular);\n\nfunction _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }\n\n// <base href=\"/\">\n\nvar heads = document.getElementsByTagName('head');\nvar base = document.createElement('base');\n\nbase.setAttribute('href', '/');\n\n_angular2.default.element(heads).append(base);\n\nexports.default = base;\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/infrastructure/base.href.config.js\n// module id = 24\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/infrastructure/base.href.config.js?");

/***/ }),
/* 25 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if (\"value\" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();\n\nfunction _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError(\"Cannot call a class as a function\"); } }\n\nvar BookmarkModel = function () {\n  BookmarkModel.$inject = [\"$http\", \"$log\", \"HateoasService\"];\n  function BookmarkModel($http, $log, HateoasService) {\n    'ngInject';\n\n    _classCallCheck(this, BookmarkModel);\n\n    this.$http = $http;\n    this.$log = $log;\n    this.HateoasService = HateoasService;\n  }\n\n  _createClass(BookmarkModel, [{\n    key: 'getBookmarks',\n    value: function getBookmarks() {\n      var _this = this;\n\n      return this.$http.get(BookmarkModel.uri()).then(function (ok) {\n        return _this.HateoasService.bookmarks(ok);\n      }, this.$log.error);\n    }\n  }], [{\n    key: 'uri',\n    value: function uri() {\n      return '/api/bookmarks';\n    }\n  }]);\n\n  return BookmarkModel;\n}();\n\nexports.default = BookmarkModel;\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/models/bookmarks.js\n// module id = 25\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/models/bookmarks.js?");

/***/ }),
/* 26 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if (\"value\" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();\n\nfunction _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError(\"Cannot call a class as a function\"); } }\n\nvar CategoryModel = function () {\n  CategoryModel.$inject = [\"$http\", \"$log\", \"HateoasService\"];\n  function CategoryModel($http, $log, HateoasService) {\n    'ngInject';\n\n    _classCallCheck(this, CategoryModel);\n\n    this.$http = $http;\n    this.$log = $log;\n    this.HateoasService = HateoasService;\n  }\n\n  _createClass(CategoryModel, [{\n    key: 'getCategories',\n    value: function getCategories() {\n      var _this = this;\n\n      return this.$http.get(CategoryModel.uri()).then(function (ok) {\n        return _this.HateoasService.categories(ok);\n      }, this.$log.error);\n    }\n  }], [{\n    key: 'uri',\n    value: function uri() {\n      return '/api/categories';\n    }\n  }]);\n\n  return CategoryModel;\n}();\n\nexports.default = CategoryModel;\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/models/categories.js\n// module id = 26\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/models/categories.js?");

/***/ }),
/* 27 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _angular = __webpack_require__(0);\n\nvar _angular2 = _interopRequireDefault(_angular);\n\nvar _bookmarks = __webpack_require__(25);\n\nvar _bookmarks2 = _interopRequireDefault(_bookmarks);\n\nvar _categories = __webpack_require__(26);\n\nvar _categories2 = _interopRequireDefault(_categories);\n\nfunction _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }\n\nexports.default = _angular2.default.module('bm.models', []).service('BookmarkModel', _bookmarks2.default).service('CategoryModel', _categories2.default);\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/models/index.js\n// module id = 27\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/models/index.js?");

/***/ }),
/* 28 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if (\"value\" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();\n\nfunction _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError(\"Cannot call a class as a function\"); } }\n\nvar HateoasService = function () {\n  function HateoasService() {\n    _classCallCheck(this, HateoasService);\n\n    this.embed = function (resp) {\n      return ((resp || {}).data || {})._embedded || {};\n    };\n  }\n\n  _createClass(HateoasService, [{\n    key: \"bookmarks\",\n    value: function bookmarks(resp) {\n      return this.embed(resp).bookmarks || [];\n    }\n  }, {\n    key: \"categories\",\n    value: function categories(resp) {\n      return this.embed(resp).categories || [];\n    }\n  }]);\n\n  return HateoasService;\n}();\n\nexports.default = HateoasService;\nmodule.exports = exports[\"default\"];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/services/hateoas.js\n// module id = 28\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/services/hateoas.js?");

/***/ }),
/* 29 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _angular = __webpack_require__(0);\n\nvar _angular2 = _interopRequireDefault(_angular);\n\nvar _hateoas = __webpack_require__(28);\n\nvar _hateoas2 = _interopRequireDefault(_hateoas);\n\nfunction _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }\n\nexports.default = _angular2.default.module('bm.services', []).service('HateoasService', _hateoas2.default);\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/services/index.js\n// module id = 29\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/services/index.js?");

/***/ }),
/* 30 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
eval("\n\nObject.defineProperty(exports, \"__esModule\", {\n  value: true\n});\n\nvar _angular = __webpack_require__(0);\n\nvar _angular2 = _interopRequireDefault(_angular);\n\nvar _application = __webpack_require__(10);\n\nvar _application2 = _interopRequireDefault(_application);\n\nfunction _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }\n\nvar angularjsOptions = {\n  // instead of data-ng-strict-di=\"\"\n  strictDi: true,\n  // instead of data-ng-cloak=\"\"\n  cloak: true\n};\n\nexports.default = _angular2.default.bootstrap(document, [_application2.default.name], angularjsOptions);\nmodule.exports = exports['default'];\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/main.js\n// module id = 30\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/main.js?");

/***/ }),
/* 31 */,
/* 32 */
/***/ (function(module, exports) {

eval("var angular=window.angular,ngModule;\ntry {ngModule=angular.module([\"ng\"])}\ncatch(e){ngModule=angular.module(\"ng\",[])}\nvar v1=\"<header>header</header>\\n<div class=\\\"container-fluid\\\">\\n<div class=\\\"row\\\">{{ $ctrl.greeting }}</div>\\n</div>\\n<footer>footer</footer>\\n\";\nvar id1=\"components/home/home.component.html\";\nvar inj=angular.element(window.document).injector();\nif(inj){inj.get(\"$templateCache\").put(id1,v1);}\nelse{ngModule.run([\"$templateCache\",function(c){c.put(id1,v1)}]);}\nmodule.exports=v1;\n\n//////////////////\n// WEBPACK FOOTER\n// ./src/application/components/home/home.component.html\n// module id = 32\n// module chunks = 0\n\n//# sourceURL=webpack:///./src/application/components/home/home.component.html?");

/***/ })
],[30]);