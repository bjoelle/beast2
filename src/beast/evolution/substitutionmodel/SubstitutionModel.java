/*
* File SubstitutionModel.java
*
* Copyright (C) 2010 Remco Bouckaert remco@cs.auckland.ac.nz
*
* This file is part of BEAST2.
* See the NOTICE file distributed with this work for additional
* information regarding copyright ownership and licensing.
*
* BEAST is free software; you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
*  BEAST is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with BEAST; if not, write to the
* Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
* Boston, MA  02110-1301  USA
*/
package beast.evolution.substitutionmodel;


import beast.core.*;
import beast.core.Input.Validate;
import beast.evolution.tree.Node;


@Description("Specifies transition probability matrix for a given distance")
public interface SubstitutionModel {

	/**
     * get the complete transition probability matrix for the given distance
     * determined as (fStartTime-fEndTime)*fRate
	 * @param node tree node for which to calculate the probabilities
	 * @param fStartTime
	 * @param fEndTime      we assume start time is larger than end time
	 * @param fRate         rate, includes gamma rates and branch rates
     * @param matrix        an array to store the matrix
	 */
	void getTransitionProbabilities(Node node, double fStartTime, double fEndTime, double fRate, double[] matrix);

    /** 
     * @return instantaneous rate matrix Q, where Q is flattened into an array
     * This is a square matrix, where rows add to zero, or null when no rate
     * matrix is available.
     */
    double [] getRateMatrix();
    
    /** return frequencies for root distribution **/
    double [] getFrequencies();

    /**
     * This function returns the Eigen decomposition of the instantaneous rate matrix if available.
     * Such Eigen decomposition may not be available because the substitution model changes over time,
     * for example, when one HKY model applies for some time t less than threshold time T while a GTR
     * model applies when t >= T.
     *  
     * @return the EigenDecomposition, null if not available
     */
    EigenDecomposition getEigenDecomposition();

    /**
     * @return whether substitution model can return complex diagonalizations
     */
    boolean canReturnComplexDiagonalization();

    public abstract class Base extends CalculationNode implements SubstitutionModel {
        public Input<Frequencies> frequencies =
                new Input<Frequencies>("frequencies", "substitution model equilibrium state frequencies", Validate.REQUIRED);

        @Override
    	public double[] getFrequencies() {
    		return frequencies.get().getFreqs();
    	}

        @Override
        public boolean canReturnComplexDiagonalization() {
            return false;
        }

        @Override
        public double [] getRateMatrix() {
        	return null;
        }
    
    
    }
}
