package org.jboss.windup.reporting;

import java.util.List;

import org.jboss.windup.config.RulePhase;
import org.jboss.windup.config.WindupRuleProvider;
import org.jboss.windup.config.query.Query;
import org.jboss.windup.graph.GraphContext;
import org.jboss.windup.reporting.freemarker.FreeMarkerOperation;
import org.jboss.windup.reporting.models.ApplicationReportModel;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;

public class RenderOverviewPageRuleProvider extends WindupRuleProvider
{
    private static final String VAR_APPLICATION_REPORTS = "applicationReports";
    private static final String OUTPUT_FILENAME = "index.html";
    private static final String TEMPLATE_PATH = "/reports/templates/overview.ftl";

    @Override
    public RulePhase getPhase()
    {
        return RulePhase.REPORT_RENDERING;
    }

    @Override
    public List<Class<? extends WindupRuleProvider>> getClassDependencies()
    {
        return generateDependencies(ApplicationReportRenderingRuleProvider.class);
    }

    @Override
    public Configuration getConfiguration(GraphContext context)
    {
        FreeMarkerOperation generateReportOperation = FreeMarkerOperation.create(TEMPLATE_PATH, OUTPUT_FILENAME,
                    VAR_APPLICATION_REPORTS);

        return ConfigurationBuilder
                    .begin()
                    .addRule()
                    .when(Query.find(ApplicationReportModel.class).as(VAR_APPLICATION_REPORTS))
                    .perform(generateReportOperation);
    }
}
