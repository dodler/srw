package lian.artyom;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static lian.artyom.RMethods.*;

/**
 * Created by artem on 27.10.15.
 */
public class RMethodsTest extends ApplicationFrame
{

    public RMethodsTest(String title)
    {
        super(title);
    }

    private JFreeChart createChart(final XYDataset dataset)
    {

        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Line Chart Demo 6",      // chart title
                "X",                      // x axis label
                "Y",                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);

        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }


    public void test3DimHist(Result3D result)
    {
        Map<Tuple, Integer> resultMap = new HashMap<>();
        for (int i = 0; i < result.tuples.length; i++)
        {
            result.tuples[i].setOperation(Tuple.CHECK_COORDINATES_WITHOUT_ORDER);
            if (resultMap.containsKey(result.tuples[i]))
            {
                resultMap.put(result.tuples[i], resultMap.get(result.tuples[i]) + result.values[i]);
            } else
            {
                resultMap.put(result.tuples[i], result.values[i]);
            }
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries colors = new XYSeries("Colors");

        System.out.println("new size:" + resultMap.entrySet().size());

        for (Map.Entry<Tuple, Integer> entry : resultMap.entrySet())
        {
            colors.add((double) entry.getKey().z, entry.getValue());
	    System.out.println(entry.getValue());
        }
	System.out.println("ready to build plot");
        dataset.addSeries(colors);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    public void test23DimHist(Result3D result)
    {
        Map<Integer, Integer> resultMap = new HashMap<>();
        for (int i = 0; i < result.tuples.length; i++)
        {
            if (resultMap.containsKey(result.tuples[i].z)
            {
                resultMap.put(result.tuples[i].z, resultMap.get(result.tuples[i]) + result.values[i]);
            } else
            {
                resultMap.put(result.tuples[i], result.values[i]);
            }
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries colors = new XYSeries("Colors");

        System.out.println("new size:" + resultMap.entrySet().size());

        for (Map.Entry<Tuple, Integer> entry : resultMap.entrySet())
        {
            colors.add((double) entry.getKey().z, entry.getValue());
	    System.out.println(entry.getValue());
        }
	System.out.println("ready to build plot");
        dataset.addSeries(colors);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }
}
