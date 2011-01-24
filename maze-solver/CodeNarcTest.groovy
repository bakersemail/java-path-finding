import groovy.grape.Grape;

Grape.grab(group:'org.codenarc', module:'CodeNarc', version:'0.12', classLoader:this.class.classLoader.rootLoader)
class CodeNarc {
    private static final GROOVY_FILES = '**/*.groovy'
    private static final RULESET_FILES = [
        'rulesets/basic.xml',
	'rulesets/braces.xml',
	'rulesets/design.xml',
        'rulesets/imports.xml'].join(',')

    def runCodeNarc() {
        def ant = new AntBuilder()

        ant.taskdef(name:'codenarc', classname:'org.codenarc.ant.CodeNarcTask')

        ant.codenarc(ruleSetFiles:RULESET_FILES,
            maxPriority1Violations:0, maxPriority2Violations:0) {

            fileset(dir:'src/main/groovy') {
               include(name:GROOVY_FILES)
            }
            fileset(dir:'src/test/groovy') {
                include(name:GROOVY_FILES)
            }

            report(type:'text') {
                option(name:'writeToStandardOut', value:true)
            }
	    report(type:'html') {
		option(name:'outputFile', value:'build/reports/CodeNarcReport.html')
            }
	    report(type:'xml') {
		option(name:'outputFile', value:'build/reports/CodeNarcReport.xml')
	    }		
        }
    }
}
new CodeNarc().runCodeNarc()
