import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Tags from '../views/Tags.vue'
import Type from '../views/Type.vue'
import Sku from '../components/skuPage.vue'
import Skus from '../views/Sku.vue'
import Nav from '../views/Nav.vue'
import PageList from '../components/productsPage.vue'



Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/type',
        name: 'type',
        component: Type
    },
    {
        path: '/list',
        name: 'list',
        component: PageList
    },
    {
        path: '/nav',
        name: 'nav',
        component: Nav
    },
    {
        path: '/tags',
        name: 'tags',
        component: Tags
    },
    {
        path: '/skus',
        name: 'skus',
        component: Skus
    },
    {
        path: '/sku',
        name: 'sku',
        component: Sku
    },
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
            import ( /* webpackChunkName: "about" */ '../views/About.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router