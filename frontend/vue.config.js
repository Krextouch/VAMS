const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true
// })

module.exports = {
  transpileDependencies: true,
  devServer: {
    proxy: 'https://api.vams.server-welt.com/'
  }
}