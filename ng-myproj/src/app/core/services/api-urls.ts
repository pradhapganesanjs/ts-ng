import { environment } from '@env';

interface ICoreServiceUrl {
     LOGIN: string;
     REPORT_TRANS: string;
     REPORTS: string;
     REPORT_BY_CRIT: string;
     UPLOAD_CONFIG_DATA: string;
     UPLOAD: string;
     REPORT_BY_LIMIT: string;
     REPORT_LIMIT_BY_CRIT: string;
     REPORT_HEADERS: string;
     REPORT_SENTLOG_HEADERS: string;
     REPORT_SENTLOG_LIMIT: string;
     REPORT_SENTLOG_LIMIT_BY_CRIT: string;
}

export const ApiUrl: ICoreServiceUrl = {

    LOGIN: `${environment.url}/api/login`,
    REPORT_TRANS: `${environment.url}/api/reportingtrans`,
    REPORTS: `${environment.url}/api/reports`,
    REPORT_BY_CRIT: `${environment.url}/api/reportsbycrit`,
    UPLOAD_CONFIG_DATA: `${environment.url}/api/saveInitialconfigData`,
    UPLOAD: `${environment.url}/api/upload`,
    REPORT_BY_LIMIT: `${environment.url}/api/reportsbylimit`,
    REPORT_LIMIT_BY_CRIT: `${environment.url}/api/reportslimitbycrit`,
    REPORT_HEADERS : `${environment.url}/api/headreporttransdoc`,

    REPORT_SENTLOG_HEADERS : `${environment.url}/api/reportsentlogheaders`,
    REPORT_SENTLOG_LIMIT: `${environment.url}/api/reportsentloglimit`,
    REPORT_SENTLOG_LIMIT_BY_CRIT: `${environment.url}/api/reportsentloglimitbycrit`
};



