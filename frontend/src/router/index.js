import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/topics/:slug',
      name: 'topic',
      component: () => import('../views/TopicView.vue'),
    },
    {
      path: '/admin/topics',
      name: 'admin-topics',
      component: () => import('../views/admin/AdminTopicsView.vue'),
    },
    {
      path: '/admin/topics/:uuid/edit',
      name: 'admin-topic-edit',
      component: () => import('../views/admin/AdminTopicEditView.vue'),
    },
    {
      path: '/admin/questions',
      name: 'admin-questions',
      component: () => import('../views/admin/AdminQuestionsView.vue'),
    },
  ],
})

export default router
