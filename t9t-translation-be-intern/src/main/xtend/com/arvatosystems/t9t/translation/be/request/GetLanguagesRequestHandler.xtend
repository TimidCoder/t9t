package com.arvatosystems.t9t.translation.be.request

import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.translation.request.GetLanguagesRequest
import com.arvatosystems.t9t.translation.request.GetLanguagesResponse
import com.arvatosystems.t9t.translation.request.UILanguageDTO
import java.util.List

class GetLanguagesRequestHandler extends AbstractReadOnlyRequestHandler<GetLanguagesRequest> {

    static List<UILanguageDTO> LANGUAGES = #[
        new UILanguageDTO('en',    'English',             'English'),
        new UILanguageDTO('de',    'German',              'Deutsch'),
        new UILanguageDTO('fr',    'French',              'Français'),
        new UILanguageDTO('es',    'Spanish',             'Español'),
        new UILanguageDTO('it',    'Italian',             'Italiano'),
        new UILanguageDTO('pt',    'Portuguese',          'Português'),
        new UILanguageDTO('pl',    'Polish',              'Polskie'),
        new UILanguageDTO('cs',    'Czech',               'České'),
        new UILanguageDTO('tr',    'Turkish',             'Türkçe'),
        new UILanguageDTO('ar',    'Arabic',              'العربية'),
        new UILanguageDTO('zh_CN', 'Chinese simplified',  '中国'),
        new UILanguageDTO('zh_TW', 'Chinese traditional', '中國')
    ]

    override GetLanguagesResponse execute(RequestContext noContext, GetLanguagesRequest rq) {
        return new GetLanguagesResponse => [
            languages = LANGUAGES
        ]
    }
}
