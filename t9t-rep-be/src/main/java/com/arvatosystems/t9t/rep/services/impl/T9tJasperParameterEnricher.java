package com.arvatosystems.t9t.rep.services.impl;

import java.util.Locale;
import java.util.Map;

import com.arvatosystems.t9t.base.output.OutputSessionParameters;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.rep.ReportParamsDTO;
import com.arvatosystems.t9t.rep.services.IJasperParameterEnricher;
import com.arvatosystems.t9t.translation.services.ITranslationProvider;
import com.google.common.base.Strings;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;
import net.sf.jasperreports.engine.JRParameter;

@Singleton
public class T9tJasperParameterEnricher implements IJasperParameterEnricher {

    private final ITranslationProvider translationProvider = Jdp.getRequired(ITranslationProvider.class);
    public static final String LANGUAGE_CODE = "languageCode";
    public static final String TRANSLATION_PROVIDER = "translator";
    public static final String TIME_ZONE = "timeZone";

    @Override
    public void enrichParameter(Map<String, Object> parameters, ReportParamsDTO reportParamsDTO,
            Map<String, Object> outputSessionAdditionalParametersList,
            OutputSessionParameters outputSessionParameters) {

        final RequestContext ctx = Jdp.getRequired(RequestContext.class);
        Locale locale = resolveReportLocale(ctx);
        parameters.put(JRParameter.REPORT_LOCALE, locale);
        parameters.put(LANGUAGE_CODE, locale.getLanguage());

        parameters.put(TRANSLATION_PROVIDER, translationProvider);

        if (reportParamsDTO != null && !Strings.isNullOrEmpty(reportParamsDTO.getTimeZone())) {
            // If timezone from reportParams exist. Use first.
            parameters.putIfAbsent(TIME_ZONE, reportParamsDTO.getTimeZone());
        }
    }

    protected Locale resolveReportLocale(RequestContext ctx) {
        Locale locale = null;
        String selectedLanguageCode = ctx.internalHeaderParameters.getLanguageCode();

        if (selectedLanguageCode != null) {
            locale = selectedLanguageCode.length() == 5
                    ? new Locale(selectedLanguageCode.substring(0, 2), selectedLanguageCode.substring(3))
                    : new Locale(selectedLanguageCode.substring(0, 2));
        } else {
            locale = Locale.US;
        }

        return locale;
    }

}
