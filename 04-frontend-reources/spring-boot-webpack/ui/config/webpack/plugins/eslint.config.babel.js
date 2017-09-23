export default env => ({
  emitError: env != 'development',
  failOnError: env != 'development',

  emitWarning: env != 'development',
  failOnWarning: env != 'development',

  configFile: './config/.eslintrc.js',
  formatter: require('eslint-friendly-formatter'),

  outputReport: {
    filePath: './dist/checkstyle.xml',
    formatter: require('eslint/lib/formatters/checkstyle')
  }
});
