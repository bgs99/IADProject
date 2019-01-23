import axios from 'axios';
import mockReports from '../mock/mreports';

export default {
  state: {
    reports: []
  },
  mutations: {
    setReports (state, list) {
      state.reports = list;
    }
  },
  actions: {
    sendReport (context, val) {
      axios('/missions/report', {
        params: val,
        method: 'POST'
      }).then(response => {
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          let duplicate = x => {
            return {
              first: x,
              second: x
            }
          };
          mockReports.unshift({
            id: -1,
            mission: duplicate(val.mission),
            subject: duplicate(val.agent),
            name: val.name,
            purpose: val.purp,
            description: val.desc,
            author: duplicate(0)
          });
        }
      });
    },
    loadReportsById (context, id) {
      context.commit('setReports', context.state.reports.filter(q => q.id === +id));
    },
    loadReportsByPage (context, page) {
      axios('/missions/reports', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setReports', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setReports', mockReports);
        }
      });
    },
    loadReportsByMission (context, mission) {
      axios('/missions/reports', {
        params: {
          id: mission
        },
        method: 'GET'
      }).then(response => {
        context.commit('setReports', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setReports', mockReports.filter(q => q.mission.first === mission));
        }
      });
    }
  },
  getters: {
    reports: state => state.reports
  }
}
