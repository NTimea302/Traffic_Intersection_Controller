package TrafficIntersectionControllerProject;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Intersection2 {
	public static void main(String[] args) {

		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Intersection 1";

		pn.NetworkPort = 1002;

		DataString green = new DataString();
		green.Printable = false;
		green.SetName("green");
		green.SetValue("green");
		pn.ConstantPlaceList.add(green);

		DataString full = new DataString();
		full.Printable = false;
		full.SetName("full");
		full.SetValue("full");
		pn.ConstantPlaceList.add(full);

		// -------------------------------------------------------------------
		// -------------------------------Lane1--------------------------------
		// --------------------------------------------------------------------

		DataCar p1 = new DataCar();
		p1.SetName("P_a1");
		pn.PlaceList.add(p1);

		DataCarQueue p2 = new DataCarQueue();
		p2.Value.Size = 3;
		p2.SetName("P_x1");
		pn.PlaceList.add(p2);

		DataString p3 = new DataString();
		p3.SetName("P_tl1");
		pn.PlaceList.add(p3);

		DataCar p4 = new DataCar();
		p4.SetName("P_b1");
		pn.PlaceList.add(p4);

		DataTransfer OP1 = new DataTransfer();
		OP1.SetName("OP1");
		OP1.Value = new TransferOperation("localhost", "1500", "in1");
		pn.PlaceList.add(OP1);

		// ----------------------------------------------------------------------------
		// ----------------------------Exit lane 1-------------------------------------
		// ----------------------------------------------------------------------------

		DataCarQueue p17 = new DataCarQueue();
		p17.Value.Size = 3;
		p17.SetName("P_o1");
		pn.PlaceList.add(p17);

		DataCar p18 = new DataCar();
		p18.SetName("P_o1Exit");
		pn.PlaceList.add(p18);

		DataTransfer PO1 = new DataTransfer();
		PO1.SetName("PO1");
		PO1.Value = new TransferOperation("localhost", "2020", "p6");
		pn.PlaceList.add(PO1);

		// -------------------------------------------------------------------------------------
		// --------------------------------Lane2-----------------------------------------------
		// -------------------------------------------------------------------------------------

		DataCar p5 = new DataCar();
		p5.SetName("P_a2");
		pn.PlaceList.add(p5);

		DataCarQueue p6 = new DataCarQueue();
		p6.Value.Size = 3;
		p6.SetName("P_x2");
		pn.PlaceList.add(p6);

		DataString p7 = new DataString();
		p7.SetName("P_tl2");
		pn.PlaceList.add(p7);

		DataCar p8 = new DataCar();
		p8.SetName("P_b2");
		pn.PlaceList.add(p8);

		DataTransfer OP2 = new DataTransfer();
		OP2.SetName("OP2");
		OP2.Value = new TransferOperation("localhost", "1500", "in2");
		pn.PlaceList.add(OP2);

		// ----------------------------------------------------------------------------
		// ----------------------------Exit lane 2-------------------------------------
		// ----------------------------------------------------------------------------

		DataCarQueue p19 = new DataCarQueue();
		p19.Value.Size = 3;
		p19.SetName("P_o2");
		pn.PlaceList.add(p19);

		DataCar p20 = new DataCar();
		p20.SetName("P_o2Exit");
		pn.PlaceList.add(p20);

		// -------------------------------------------------------------------------------------
		// --------------------------------Lane3-----------------------------------------------
		// -------------------------------------------------------------------------------------

		DataCar p9 = new DataCar();
		p9.SetName("P_a3");
		pn.PlaceList.add(p9);

		DataCarQueue p10 = new DataCarQueue();
		p10.Value.Size = 3;
		p10.SetName("P_x3");
		pn.PlaceList.add(p10);

		DataString p11 = new DataString();
		p11.SetName("P_tl3");
		pn.PlaceList.add(p11);

		DataCar p12 = new DataCar();
		p12.SetName("P_b3");
		pn.PlaceList.add(p12);

		DataTransfer OP3 = new DataTransfer();
		OP3.SetName("OP3");
		OP3.Value = new TransferOperation("localhost", "1500", "in3");
		pn.PlaceList.add(OP3);

		// ----------------------------------------------------------------------------
		// ----------------------------Exit lane 3-------------------------------------
		// ----------------------------------------------------------------------------

		DataCarQueue p21 = new DataCarQueue();
		p21.Value.Size = 3;
		p21.SetName("P_o3");
		pn.PlaceList.add(p21);

		DataCar p22 = new DataCar();
		p22.SetName("P_o3Exit");
		pn.PlaceList.add(p22);

		// -------------------------------------------------------------------------------------------
		// --------------------------------Intersection-----------------------------------------------
		// -------------------------------------------------------------------------------------------

		DataCarQueue p25 = new DataCarQueue();
		p25.Value.Size = 3;
		p25.SetName("PI");
		pn.PlaceList.add(p25);

		// -------------------------------------------------------------------------------------------
		// --------------------------------Transitions-----------------------------------------------
		// -------------------------------------------------------------------------------------------

		// ---------------------------------First Lane
		// Input+Output-----------------------------------

		// T_u1 ------------------------------------------------
		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "T_u1";
		t1.InputPlaceName.add("P_a1");
		t1.InputPlaceName.add("P_x1");

		Condition T1Ct1 = new Condition(t1, "P_a1", TransitionCondition.NotNull);
		Condition T1Ct2 = new Condition(t1, "P_x1", TransitionCondition.CanAddCars);
		T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

		GuardMapping grdT1 = new GuardMapping();
		grdT1.condition = T1Ct1;

		grdT1.Activations.add(new Activation(t1, "P_a1", TransitionOperation.AddElement, "P_x1"));
		t1.GuardMappingList.add(grdT1);

		Condition T1Ct3 = new Condition(t1, "P_a1", TransitionCondition.NotNull);
		Condition T1Ct4 = new Condition(t1, "P_x1", TransitionCondition.CanNotAddCars);
		T1Ct3.SetNextCondition(LogicConnector.AND, T1Ct4);

		GuardMapping grdT1_1 = new GuardMapping();
		grdT1_1.condition = T1Ct3;
		grdT1_1.Activations.add(new Activation(t1, "full", TransitionOperation.SendOverNetwork, "OP1"));
		grdT1_1.Activations.add(new Activation(t1, "P_a1", TransitionOperation.Move, "P_a1"));
		t1.GuardMappingList.add(grdT1_1);

		t1.Delay = 0;
		pn.Transitions.add(t1);

		// T_e1 ------------------------------------------------
		PetriTransition t2 = new PetriTransition(pn);
		t2.TransitionName = "T_e1";
		t2.InputPlaceName.add("P_x1");
		t2.InputPlaceName.add("P_tl1");

		Condition T2Ct1 = new Condition(t2, "P_tl1", TransitionCondition.Equal, "green");
		Condition T2Ct2 = new Condition(t2, "P_x1", TransitionCondition.HaveCar);
		T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

		GuardMapping grdT2 = new GuardMapping();
		grdT2.condition = T2Ct1;
		grdT2.Activations.add(new Activation(t2, "P_x1", TransitionOperation.PopElementWithoutTarget, "P_b1"));
		grdT2.Activations.add(new Activation(t2, "P_tl1", TransitionOperation.Move, "P_tl1"));

		t2.GuardMappingList.add(grdT2);

		t2.Delay = 0;
		pn.Transitions.add(t2);

		// T_i1 ------------------------------------------------
		PetriTransition t3 = new PetriTransition(pn);
		t3.TransitionName = "T_i1";
		t3.InputPlaceName.add("P_b1");
		t3.InputPlaceName.add("P_I");

		Condition T3Ct1 = new Condition(t3, "P_b1", TransitionCondition.NotNull);
		Condition T3Ct2 = new Condition(t3, "P_I", TransitionCondition.CanAddCars);
		T3Ct1.SetNextCondition(LogicConnector.AND, T3Ct2);

		GuardMapping grdT3 = new GuardMapping();
		grdT3.condition = T3Ct1;
		grdT3.Activations.add(new Activation(t3, "P_b1", TransitionOperation.AddElement, "P_I"));
		t3.GuardMappingList.add(grdT3);

		t3.Delay = 0;
		pn.Transitions.add(t3);

		// T_g1-----------------------------------------------------------
		PetriTransition t4 = new PetriTransition(pn);
		t4.TransitionName = "T_g1";
		t4.InputPlaceName.add("P_I");
		t4.InputPlaceName.add("P_o1");

		Condition T4Ct1 = new Condition(t4, "P_I", TransitionCondition.HaveCarForMe);
		Condition T4Ct2 = new Condition(t4, "P_o1", TransitionCondition.CanAddCars);
		T4Ct1.SetNextCondition(LogicConnector.AND, T4Ct2);

		GuardMapping grdT4 = new GuardMapping();
		grdT4.condition = T4Ct1;
		grdT4.Activations.add(new Activation(t4, "P_I", TransitionOperation.PopElementWithTargetToQueue, "P_o1"));
		t4.GuardMappingList.add(grdT4);

		t4.Delay = 0;
		pn.Transitions.add(t4);

		// T_g1Exit----------------------------------------------------------------

		PetriTransition t5 = new PetriTransition(pn);
		t5.TransitionName = "T_g1Exit";
		t5.InputPlaceName.add("P_o1");

		Condition t5Ct1 = new Condition(t5, "P_o1", TransitionCondition.HaveCar);

		GuardMapping grdt5 = new GuardMapping();
		grdt5.condition = t5Ct1;
		grdt5.Activations.add(new Activation(t5, "P_o1", TransitionOperation.PopElementWithoutTarget, "P_o1Exit"));
		t5.GuardMappingList.add(grdt5);

		t5.Delay = 0;
		pn.Transitions.add(t5);

		// ---------------------------------Second Lane
		// Input+Output-----------------------------------
		// -------------------------------------------------------------------------------------

		// T_u2 ------------------------------------------------
		PetriTransition t6 = new PetriTransition(pn);
		t6.TransitionName = "T_u2";
		t6.InputPlaceName.add("P_a2");
		t6.InputPlaceName.add("P_x2");

		Condition t6Ct1 = new Condition(t6, "P_a2", TransitionCondition.NotNull);
		Condition t6Ct2 = new Condition(t6, "P_x2", TransitionCondition.CanAddCars);
		t6Ct1.SetNextCondition(LogicConnector.AND, t6Ct2);

		GuardMapping grdt6 = new GuardMapping();
		grdt6.condition = t6Ct1;
		grdt6.Activations.add(new Activation(t6, "P_a2", TransitionOperation.AddElement, "P_x2"));

		Condition T2Ct6 = new Condition(t6, "P_a2", TransitionCondition.NotNull);
		Condition T2Ct4 = new Condition(t6, "P_x2", TransitionCondition.CanNotAddCars);
		T2Ct6.SetNextCondition(LogicConnector.AND, T2Ct4);

		GuardMapping grdT2_1 = new GuardMapping();
		grdT2_1.condition = T2Ct6;
		grdT2_1.Activations.add(new Activation(t6, "full", TransitionOperation.SendOverNetwork, "OP2"));
		grdT2_1.Activations.add(new Activation(t6, "P_a2", TransitionOperation.Move, "P_a2"));
		t6.GuardMappingList.add(grdT2_1);

		t6.GuardMappingList.add(grdt6);

		t6.Delay = 0;
		pn.Transitions.add(t6);

		// T_e2 ------------------------------------------------
		PetriTransition t7 = new PetriTransition(pn);
		t7.TransitionName = "T_e2";
		t7.InputPlaceName.add("P_x2");
		t7.InputPlaceName.add("P_tl2");

		Condition t7Ct1 = new Condition(t7, "P_tl2", TransitionCondition.Equal, "green");
		Condition t7Ct2 = new Condition(t7, "P_x2", TransitionCondition.HaveCar);
		t7Ct1.SetNextCondition(LogicConnector.AND, t7Ct2);

		GuardMapping grdt7 = new GuardMapping();
		grdt7.condition = t7Ct1;
		grdt7.Activations.add(new Activation(t7, "P_x2", TransitionOperation.PopElementWithoutTarget, "P_b2"));
		grdt7.Activations.add(new Activation(t7, "P_tl2", TransitionOperation.Move, "P_tl2"));
		t7.GuardMappingList.add(grdT2);

		t7.Delay = 0;
		pn.Transitions.add(t7);

		// T_i2 ------------------------------------------------
		PetriTransition t8 = new PetriTransition(pn);
		t8.TransitionName = "T_i2";
		t8.InputPlaceName.add("P_b2");
		t8.InputPlaceName.add("P_I");

		Condition t8Ct1 = new Condition(t8, "P_b2", TransitionCondition.NotNull);
		Condition t8Ct2 = new Condition(t8, "P_I", TransitionCondition.CanAddCars);
		t8Ct1.SetNextCondition(LogicConnector.AND, t8Ct2);

		GuardMapping grdt8 = new GuardMapping();
		grdt8.condition = t8Ct1;
		grdt8.Activations.add(new Activation(t8, "P_b2", TransitionOperation.AddElement, "P_I"));
		t8.GuardMappingList.add(grdt8);

		t8.Delay = 0;
		pn.Transitions.add(t8);

		// T_g2Exit----------------------------------------------------------------

		PetriTransition t15 = new PetriTransition(pn);
		t15.TransitionName = "T_g2Exit";
		t15.InputPlaceName.add("P_o2");

		Condition t15Ct1 = new Condition(t15, "P_o2", TransitionCondition.HaveCar);

		GuardMapping grdt15 = new GuardMapping();
		grdt15.condition = t15Ct1;
		grdt15.Activations.add(new Activation(t15, "P_o2", TransitionOperation.PopElementWithoutTarget, "P_o2Exit"));
		t15.GuardMappingList.add(grdt15);

		t15.Delay = 0;
		pn.Transitions.add(t15);

		// T_g2-----------------------------------------------------------
		PetriTransition t16 = new PetriTransition(pn);
		t16.TransitionName = "T_g2";
		t16.InputPlaceName.add("P_I");
		t16.InputPlaceName.add("P_o2");

		Condition T16Ct1 = new Condition(t16, "P_I", TransitionCondition.HaveCarForMe);
		Condition T16Ct2 = new Condition(t16, "P_o2", TransitionCondition.CanAddCars);
		T16Ct1.SetNextCondition(LogicConnector.AND, T16Ct2);

		GuardMapping grdT16 = new GuardMapping();
		grdT16.condition = T16Ct1;
		grdT16.Activations.add(new Activation(t16, "P_I", TransitionOperation.PopElementWithTargetToQueue, "P_o2"));
		t16.GuardMappingList.add(grdT16);

		t16.Delay = 0;
		pn.Transitions.add(t16);

		// T15 ------------------------------------------------
		PetriTransition t17 = new PetriTransition(pn);
		t17.TransitionName = "T_i2";
		t17.InputPlaceName.add("P_b2");
		t17.InputPlaceName.add("P_I");

		Condition t17Ct1 = new Condition(t17, "P_b2", TransitionCondition.NotNull);
		Condition t17Ct2 = new Condition(t17, "P_I", TransitionCondition.CanAddCars);
		t17Ct1.SetNextCondition(LogicConnector.AND, t17Ct2);

		GuardMapping grdt17 = new GuardMapping();
		grdt17.condition = t17Ct1;
		grdt17.Activations.add(new Activation(t17, "P_b2", TransitionOperation.AddElement, "P_I"));
		t17.GuardMappingList.add(grdt17);

		t17.Delay = 0;
		pn.Transitions.add(t17);

		// ---------------------------------Third Lane
		// Input + Output-------------------------
		// -------------------------------------------------------------------------------------

		// T_u3 ------------------------------------------------
		PetriTransition t9 = new PetriTransition(pn);
		t9.TransitionName = "T_u3";
		t9.InputPlaceName.add("P_a3");
		t9.InputPlaceName.add("P_x3");

		Condition t9Ct1 = new Condition(t9, "P_a3", TransitionCondition.NotNull);
		Condition t9Ct2 = new Condition(t9, "P_x3", TransitionCondition.CanAddCars);
		t9Ct1.SetNextCondition(LogicConnector.AND, t9Ct2);

		GuardMapping grdt9 = new GuardMapping();
		grdt9.condition = t9Ct1;
		grdt9.Activations.add(new Activation(t9, "P_a3", TransitionOperation.AddElement, "P_x3"));

		Condition t9Ct3 = new Condition(t9, "P_a1", TransitionCondition.NotNull);
		Condition t9Ct4 = new Condition(t9, "P_x1", TransitionCondition.CanNotAddCars);
		t9Ct3.SetNextCondition(LogicConnector.AND, t9Ct4);

		GuardMapping grdT3_1 = new GuardMapping();
		grdT3_1.condition = t9Ct3;
		grdT3_1.Activations.add(new Activation(t9, "full", TransitionOperation.SendOverNetwork, "OP3"));
		grdT3_1.Activations.add(new Activation(t9, "P_a3", TransitionOperation.Move, "P_a3"));
		t9.GuardMappingList.add(grdT3_1);

		t9.GuardMappingList.add(grdt9);

		t9.Delay = 0;
		pn.Transitions.add(t9);

		// T_e3 ------------------------------------------------
		PetriTransition t10 = new PetriTransition(pn);
		t10.TransitionName = "T_e3";
		t10.InputPlaceName.add("P_x3");
		t10.InputPlaceName.add("P_tl3");

		Condition t10Ct1 = new Condition(t10, "P_tl3", TransitionCondition.Equal, "green");
		Condition t10Ct2 = new Condition(t10, "P_x3", TransitionCondition.HaveCar);
		t10Ct1.SetNextCondition(LogicConnector.AND, t10Ct2);

		GuardMapping grdt10 = new GuardMapping();
		grdt10.condition = t10Ct1;
		grdt10.Activations.add(new Activation(t10, "P_x3", TransitionOperation.PopElementWithoutTarget, "P_b3"));
		grdt10.Activations.add(new Activation(t10, "P_tl3", TransitionOperation.Move, "P_tl3"));
		t10.GuardMappingList.add(grdt10);

		t10.Delay = 0;
		pn.Transitions.add(t10);

		// T_i3 ------------------------------------------------
		PetriTransition t11 = new PetriTransition(pn);
		t11.TransitionName = "T_i3";
		t11.InputPlaceName.add("P_b3");
		t11.InputPlaceName.add("P_I");

		Condition t11Ct1 = new Condition(t11, "P_b3", TransitionCondition.NotNull);
		Condition t11Ct2 = new Condition(t11, "P_I", TransitionCondition.CanAddCars);
		t11Ct1.SetNextCondition(LogicConnector.AND, t11Ct2);

		GuardMapping grdt11 = new GuardMapping();
		grdt11.condition = t11Ct1;
		grdt11.Activations.add(new Activation(t11, "P_b3", TransitionOperation.AddElement, "P_I"));
		t11.GuardMappingList.add(grdt11);

		t11.Delay = 0;
		pn.Transitions.add(t11);

		// T_g3---------------------------------------------------------

		PetriTransition t13 = new PetriTransition(pn);
		t13.TransitionName = "T_g3";
		t13.InputPlaceName.add("P_I");
		t13.InputPlaceName.add("P_o3");

		Condition t13Ct1 = new Condition(t13, "P_I", TransitionCondition.HaveCarForMe);
		Condition t13Ct2 = new Condition(t13, "P_o3", TransitionCondition.CanAddCars);
		t13Ct1.SetNextCondition(LogicConnector.AND, t13Ct2);

		GuardMapping grdt13 = new GuardMapping();
		grdt13.condition = t13Ct1;
		grdt13.Activations.add(new Activation(t13, "P_I", TransitionOperation.PopElementWithTargetToQueue, "P_o3"));
		t13.GuardMappingList.add(grdt13);

		// T_g3Exit----------------------------------------------------------------

		PetriTransition t12 = new PetriTransition(pn);
		t12.TransitionName = "T_g3Exit";
		t12.InputPlaceName.add("P_o3");

		Condition t12Ct1 = new Condition(t12, "P_o3", TransitionCondition.HaveCar);

		GuardMapping grdt12 = new GuardMapping();
		grdt12.condition = t12Ct1;
		grdt12.Activations.add(new Activation(t12, "P_o3", TransitionOperation.PopElementWithoutTarget, "P_o3Exit"));
		t12.GuardMappingList.add(grdt12);

		t12.Delay = 0;
		pn.Transitions.add(t12);

		t13.Delay = 0;
		pn.Transitions.add(t13);

		// T_out---------------------------------------------------------
		PetriTransition t14 = new PetriTransition(pn);
		t14.TransitionName = "T_out";
		t14.InputPlaceName.add("P_o1Exit");

		Condition t14Ct1 = new Condition(t14, "P_o1Exit", TransitionCondition.NotNull);

		GuardMapping grdt14 = new GuardMapping();
		grdt14.condition = t14Ct1;
		grdt14.Activations.add(new Activation(t14, "P_o1Exit", TransitionOperation.SendOverNetwork, "PO1"));
		t14.GuardMappingList.add(grdt14);

		t14.Delay = 0;
		pn.Transitions.add(t14);

		// -------------------------------------------------------------------------------------
		// ----------------------------PNStart-------------------------------------------------
		// -------------------------------------------------------------------------------------

		System.out.println("Start: Intersection 2\n");
		pn.Delay = 2000;
		// pn.Start();

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);
	}
}