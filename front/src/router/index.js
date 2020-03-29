import Vue from 'vue'
import Router from 'vue-router'
import Map from '../components/Map'
import Cards from '../components/Cards'
import Wizard from '../components/Wizard'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/missions/new/:id',
      component: Wizard
    },
    {
      path: '/',
      alias: '/map',
      redirect: '/map/0',
      exact: true
    },
    {
      path: '/map/:id',
      component: Map,
      name: 'map',
      exact: true
    },
    {
      path: '/:collection/:filter/:source/:id',
      component: Cards
    },
    {
      path: '/:collection/:source/:id',
      redirect: '/:collection/all/:source/:id'
    }
  ]
})
